import tensorflow as tf
from tensorflow.keras.applications import MobileNetV2
from tensorflow.keras.layers import Input, GlobalAveragePooling2D, Dropout, Dense
from tensorflow.keras import Model
import tensorflow_datasets as tfds

# Load MNIST
(ds_train, ds_test), ds_info = tfds.load('stanford_dogs',
                                         split=['train', 'test'],
                                         shuffle_files=True,
                                         as_supervised=True,
                                         with_info=True)
print(ds_info)


def preprocessing(image, label):
    size = (224, 224)
    image = tf.image.resize(image, size)
    return tf.cast(image, tf.float32) / 255., label


# Build training pipeline
ds_train = ds_train.map(
    preprocessing, num_parallel_calls=tf.data.experimental.AUTOTUNE)
ds_train = ds_train.cache()
ds_train = ds_train.shuffle(1000)
ds_train = ds_train.batch(32)
ds_train = ds_train.prefetch(tf.data.experimental.AUTOTUNE)
ds_train = ds_train.repeat()

# Build evaluation pipeline
ds_test = ds_test.map(
    preprocessing, num_parallel_calls=tf.data.experimental.AUTOTUNE)
ds_test = ds_test.batch(32)
ds_test = ds_test.cache()
ds_test = ds_test.prefetch(tf.data.experimental.AUTOTUNE)

# Create and train the model
base_model = MobileNetV2(
    weights="imagenet",
    input_shape=(224, 224, 3),
    include_top=False,
)
base_model.trainable = False    # Freeze the base_model
x = base_model(base_model.input, training=False)
x = GlobalAveragePooling2D()(x)
x = Dropout(0.2)(x)
outputs = Dense(120, activation='softmax')(x)
model = Model(base_model.input, outputs)
model.summary()
model.compile(
    loss=tf.keras.losses.SparseCategoricalCrossentropy(),
    optimizer=tf.keras.optimizers.Adam(0.001),
    metrics=['accuracy'],
)
model.fit(
    ds_train,
    epochs=5,
    steps_per_epoch=ds_info.splits['train'].num_examples // 32,
    validation_data=ds_test,
)
