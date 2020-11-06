package com.example.ipets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class bottomSheetNotePreview extends BottomSheetDialogFragment {

    private String title;
    private String content;
    public bottomSheetNotePreview(String title, String content){
        this.title = title;
        this.content = content;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_notepreview, container, false);
        TextView txtTitle = v.findViewById(R.id.editNoteText);
        txtTitle.setText(this.title);
        TextView txtContent = v.findViewById(R.id.noteContentText);
        txtContent.setText(this.content);
        return v;
    }
}
