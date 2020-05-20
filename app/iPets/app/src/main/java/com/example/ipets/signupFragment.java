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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class signupFragment extends Fragment {
    private String userUID;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    public signupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Button next1 = getView().findViewById(R.id.next1);
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }
    private void register() {
        EditText edemail = getView().findViewById(R.id.email);
        final EditText edpassword = getView().findViewById(R.id.addpw);
        final EditText edconfirmpw = getView().findViewById(R.id.confirmpw);
        String email = edemail.getText().toString();
        String password = edpassword.getText().toString();
        String confirmpw = edconfirmpw.getText().toString();
        if(password.length()<6){
            AlertDialog.Builder passworderror = new AlertDialog.Builder(getActivity());
            passworderror.setMessage("密碼長度不可<6個字元");
            passworderror.setNegativeButton("重新輸入", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    edpassword.setText("");
                }
            });
            passworderror.show();
        }else if(password.equals(confirmpw)!= true){
            AlertDialog.Builder confirmpwerror = new AlertDialog.Builder(getActivity());
            confirmpwerror.setMessage("密碼不符");
            confirmpwerror.setNegativeButton("重新輸入", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    edpassword.setText("");
                    edconfirmpw.setText("");
                }
            });
            confirmpwerror.show();
        }else {
            createUser(email, password);
        }
    }
    private void createUser(final String email, final String password) {
       // 呼叫FirebaseAuth類別所提供建立帳號的方法createUserWithEmailAndPassword，傳入email與password字串。
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            //當建立帳號工作完成後會自動呼叫onComplete方法
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    final EditText edusername = getView().findViewById(R.id.username);
                                    String username = edusername.getText().toString();
                                    userUID = auth.getUid();
                                    FirebaseFirestore db;
                                    db = FirebaseFirestore.getInstance();
                                    Map<String, Object> userInfo = new HashMap<>();
                                    userInfo.put("Username", username);
                                    userInfo.put("Email", email);
                                    userInfo.put("Password", password);
                                    db.collection("userInformation").document(userUID).set(userInfo);
                                    NavController controller = Navigation.findNavController(getView());
                                    controller.navigate(R.id.action_signupFragment_to_userinfoFragment);
                                }else{
                                    AlertDialog.Builder emailerror = new AlertDialog.Builder(getActivity());
                                    emailerror.setMessage("此信箱已使用過或信箱格式錯誤");
                                    emailerror.setNegativeButton("重新輸入", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            // TODO Auto-generated method stub
                                            EditText edemail = getView().findViewById(R.id.email);
                                            edemail.setText("");
                                        }
                                    });
                                    emailerror.show();
                                }
                            }
                        });
    }

}
