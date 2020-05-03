package com.example.ipets;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class loginFragment extends Fragment {


    public loginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button login = getView().findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_loginFragment_to_homeFragment);
            }
        });
        super.onActivityCreated(savedInstanceState);
        Button forgot = getView().findViewById(R.id.forgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_loginFragment_to_forgotFragment);
            }
        });
        super.onActivityCreated(savedInstanceState);
        Button signup = getView().findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_loginFragment_to_signupFragment);
            }
        });
        Button sent = getView().findViewById(R.id.login);
        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReadSingleContact(view);
            }
        });
    }

        private void ReadSingleContact(final View view) {
            final FirebaseFirestore db = FirebaseFirestore.getInstance();
            final EditText edaccount = getView().findViewById(R.id.username);
            final EditText edpassword = getView().findViewById(R.id.password);
            String account = edaccount.getText().toString();
            final String password = edpassword.getText().toString();
            DocumentReference user = db.collection("userInformation").document(account);
            user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        StringBuilder fields = new StringBuilder("");
                        fields.append(doc.get("password"));
                        String query = fields.toString();
                        if (query.equals(password)) {
                            NavController controller = Navigation.findNavController(view);
                            controller.navigate(R.id.action_loginFragment_to_homeFragment);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("登入失敗");
                            builder.setMessage("基帳號或密碼錯誤,大小血虛相符");
                            builder.setNegativeButton("重新輸入", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    // TODO Auto-generated method stub
                                    edaccount.setText("");
                                    edpassword.setText("");
                                }
                            });
                            builder.setCancelable(true);   //设置按钮是否可以按返回键取消,false则不可以取消
                            AlertDialog dialog = builder.create();  //创建对话框
                            dialog.setCanceledOnTouchOutside(true);      //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
                            dialog.show();
                        }
                    }
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
}


