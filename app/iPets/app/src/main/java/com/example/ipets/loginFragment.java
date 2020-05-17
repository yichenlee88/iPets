package com.example.ipets;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class loginFragment extends Fragment {
    private String userUID;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

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
        Button sent = getView().findViewById(R.id.login);
        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });
        Button signup = getView().findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_loginFragment_to_signupFragment);
            }
        });
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser!=null) {
            NavController controller = Navigation.findNavController(getView());
            controller.navigate(R.id.action_loginFragment_to_homeFragment);
        }

        /*
        super.onActivityCreated(savedInstanceState);
        Button forgot = getView().findViewById(R.id.forgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_loginFragment_to_forgotFragment);
            }
        });
        */
    }

    public void login(final View v){
        final EditText edpassword = getView().findViewById(R.id.password);
        final EditText edaccount = getView().findViewById(R.id.account);
        String account = edaccount.getText().toString();
        final String password = edpassword.getText().toString();
        Log.d("AUTH", account+"/"+password);
        //呼叫FirebaseAuth類別的signInWithEmailAndPassword方法進行帳號與密碼的登入
        auth.signInWithEmailAndPassword(account, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    Log.d("onComplete", "登入失敗");
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
                }else{
                    NavController controller = Navigation.findNavController(v);
                    controller.navigate(R.id.action_loginFragment_to_homeFragment);
                }
            }
        });
    }
}


