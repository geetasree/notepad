package com.example.welcome.smartnotepad.Adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.welcome.smartnotepad.DBModel.NotepadModel;
import com.example.welcome.smartnotepad.R;

import java.util.ArrayList;

/**
 * Created by Welcome on 20-Aug-17.
 */

public class ListNoteAdapter extends ArrayAdapter {
    Context mContext;
    ArrayList<NotepadModel> mNoteList;
    TextView tvId;
    TextView tvTitle;
    TextView tvDescription;
    TextView tvTs;
    private SparseBooleanArray selectedListItemsIds;

    public ListNoteAdapter(Context mContext, ArrayList<NotepadModel> mNoteList) {
        super(mContext,R.layout.note_listview,mNoteList);
        Log.w("++++ ListNoteAdapter: ","ListNoteAdapter");
        this.mContext = mContext;
        this.mNoteList = mNoteList;
        selectedListItemsIds = new SparseBooleanArray();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.note_listview, null);

        tvId = (TextView)view.findViewById(R.id.tv_note_id);
        tvTitle = (TextView)view.findViewById(R.id.tv_note_title);
        tvDescription = (TextView)view.findViewById(R.id.tv_note_description);
        tvTs = (TextView)view.findViewById(R.id.tv_note_ts);

        tvId.setText(mNoteList.get(i).getId()+"");
        tvTitle.setText(mNoteList.get(i).getTitle());
        tvDescription.setText(mNoteList.get(i).getDescription());
        tvTs.setText(String.valueOf(mNoteList.get(i).getTs()));
        return view;
    }

    public void toggleSelection(int position) {
        selectView(position, !selectedListItemsIds.get(position));
    }
    public void selectView(int position, boolean value) {
        if (value)
            selectedListItemsIds.put(position, value);
        else
            selectedListItemsIds.delete(position);
        notifyDataSetChanged();
    }
}
