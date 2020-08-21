import binascii
import io
import json
import os
import sys

import numpy as np
import tensorflow as tf
from cv2 import cv2
from PIL import Image

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

classes_name = ["Chihuaha",
                "Japanese Spaniel",
                "Maltese Dog",
                "Pekinese",
                "Shih-Tzu",
                "Blenheim Spaniel",
                "Papillon",
                "Toy Terrier",
                "Rhodesian Ridgeback",
                "Afghan Hound",
                "Basset Hound",
                "Beagle",
                "Bloodhound",
                "Bluetick",
                "Black-and-tan Coonhound",
                "Walker Hound",
                "English Foxhound",
                "Redbone",
                "Borzoi",
                "Irish Wolfhound",
                "Italian Greyhound",
                "Whippet",
                "Ibizian Hound",
                "Norwegian Elkhound",
                "Otterhound",
                "Saluki",
                "Scottish Deerhound",
                "Weimaraner",
                "Staffordshire Bullterrier",
                "American Staffordshire Terrier",
                "Bedlington Terrier",
                "Border Terrier",
                "Kerry Blue Terrier",
                "Irish Terrier",
                "Norfolk Terrier",
                "Norwich Terrier",
                "Yorkshire Terrier",
                "Wirehaired Fox Terrier",
                "Lakeland Terrier",
                "Sealyham Terrier",
                "Airedale",
                "Cairn",
                "Australian Terrier",
                "Dandi Dinmont",
                "Boston Bull",
                "Miniature Schnauzer",
                "Giant Schnauzer",
                "Standard Schnauzer",
                "Scotch Terrier",
                "Tibetan Terrier",
                "Silky Terrier",
                "Soft-coated Wheaten Terrier",
                "West Highland White Terrier",
                "Lhasa",
                "Flat-coated Retriever",
                "Curly-coater Retriever",
                "Golden Retriever",
                "Labrador Retriever",
                "Chesapeake Bay Retriever",
                "German Short-haired Pointer",
                "Vizsla",
                "English Setter",
                "Irish Setter",
                "Gordon Setter",
                "Brittany",
                "Clumber",
                "English Springer Spaniel",
                "Welsh Springer Spaniel",
                "Cocker Spaniel",
                "Sussex Spaniel",
                "Irish Water Spaniel",
                "Kuvasz",
                "Schipperke",
                "Groenendael",
                "Malinois",
                "Briard",
                "Kelpie",
                "Komondor",
                "Old English Sheepdog",
                "Shetland Sheepdog",
                "Collie",
                "Border Collie",
                "Bouvier des Flandres",
                "Rottweiler",
                "German Shepard",
                "Doberman",
                "Miniature Pinscher",
                "Greater Swiss Mountain Dog",
                "Bernese Mountain Dog",
                "Appenzeller",
                "EntleBucher",
                "Boxer",
                "Bull Mastiff",
                "Tibetan Mastiff",
                "French Bulldog",
                "Great Dane",
                "Saint Bernard",
                "Eskimo Dog",
                "Malamute",
                "Siberian Husky",
                "Affenpinscher",
                "Basenji",
                "Pug",
                "Leonberg",
                "Newfoundland",
                "Great Pyrenees",
                "Samoyed",
                "Pomeranian",
                "Chow",
                "Keeshond",
                "Brabancon Griffon",
                "Pembroke",
                "Cardigan",
                "Toy Poodle",
                "Miniature Poodle",
                "Standard Poodle",
                "Mexican Hairless",
                "Dingo",
                "Dhole",
                "African Hunting Dog"]


def main():
    bytes_string = binascii.a2b_hex(sys.stdin.readlines()[0].rstrip())
    imageStream = io.BytesIO(bytes_string)
    img = Image.open(imageStream).convert('RGB')
    img = np.array(img)
    h, w, c = img.shape

    img = cv2.resize(img, (224, 224)) / 255.0

    model = tf.keras.models.load_model('dog_breed_classification/dog.h5')

    y_pred = model.predict(img[np.newaxis, ...]).ravel()
    top5_idx = np.argsort(y_pred)[::-1][:5]    # top 5
    top5 = {classes_name[i]: y_pred[i] for i in top5_idx}
    print(json.dumps(str(top5).replace('\'', '^')))
    # print(json.dumps(str(top5)))
    # print(y_pred)


if __name__ == '__main__':
    main()
