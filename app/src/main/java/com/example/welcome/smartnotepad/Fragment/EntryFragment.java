package com.example.welcome.smartnotepad.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.welcome.smartnotepad.DBManager.NotepadManager;
import com.example.welcome.smartnotepad.DBModel.NotepadModel;
import com.example.welcome.smartnotepad.R;

import java.text.DateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EntryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText edtTitle;
    EditText edtDescription;
    Button btnSaveData;
    NotepadModel objNotepadModel;
    NotepadManager objNotepadmanager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View RootView;
    int noteId;
    Fragment currentFragment;
    FragmentManager manager;
    FragmentTransaction transaction;

    private OnFragmentInteractionListener mListener;

    public EntryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EntryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EntryFragment newInstance(String param1, String param2) {
        EntryFragment fragment = new EntryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.fragment_entry, container, false);
        noteId = getArguments().getInt("modifyId",-1);
        Log.d("***noteId",noteId+"");
        edtTitle = (EditText) RootView.findViewById(R.id.edtTitle);
        edtDescription = (EditText) RootView.findViewById(R.id.edtDescription);
        btnSaveData = (Button) RootView.findViewById(R.id.btnSave);
        if(noteId!=-1) {
            objNotepadmanager=new NotepadManager(getActivity().getBaseContext());
            NotepadModel noteInfo= objNotepadmanager.getSpecificNote(noteId);
            edtTitle.setText(noteInfo.getTitle());
            edtDescription.setText(noteInfo.getDescription());
        }
        btnSaveData.setOnClickListener(this);
        return RootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private boolean emptyCheck() {
        if(TextUtils.isEmpty(edtTitle.getText()) || TextUtils.isEmpty(edtDescription.getText())){
            edtTitle.setError("Please give both Title & Description");
            return false;
        }
        else
            return true;
    }

   @Override
    public void onClick(View view) {
       if(emptyCheck()) {
           //  Toast.makeText(getActivity().getBaseContext(),"value: "+ noteId+"", Toast.LENGTH_SHORT).show();
           if (noteId == -1) {
               objNotepadModel = new NotepadModel(edtTitle.getText().toString(), edtDescription.getText().toString());
               objNotepadmanager = new NotepadManager(getActivity());
               boolean value = objNotepadmanager.insertData(objNotepadModel);
               if (value) {
                   Toast.makeText(getActivity().getBaseContext(), "inserted", Toast.LENGTH_SHORT).show();
                   currentFragment = new ListFragment();
                   manager = getFragmentManager();
                   transaction = manager.beginTransaction();
                   transaction.replace(R.id.llFragmentHolder, currentFragment);
                   transaction.commit();
               } else
                   Toast.makeText(getActivity().getBaseContext(), "not inserted", Toast.LENGTH_SHORT).show();
           } else {
               objNotepadModel = new NotepadModel(noteId, edtTitle.getText().toString(), edtDescription.getText().toString(), DateFormat.getDateTimeInstance().format(new Date()));
               objNotepadmanager = new NotepadManager(getActivity());
               boolean value = objNotepadmanager.updateData(objNotepadModel);
               if (value) {
                   Toast.makeText(getActivity().getBaseContext(), "updated", Toast.LENGTH_SHORT).show();
                   currentFragment = new ListFragment();
                   manager = getFragmentManager();
                   transaction = manager.beginTransaction();
                   transaction.replace(R.id.llFragmentHolder, currentFragment);
                   transaction.commit();
               } else
                   Toast.makeText(getActivity().getBaseContext(), "not updated", Toast.LENGTH_SHORT).show();
           }
       }
       currentFragment = new ListFragment();
       manager = getFragmentManager();
       transaction = manager.beginTransaction();
       transaction.add(R.id.llFragmentHolder, currentFragment);
       transaction.addToBackStack(null);
       transaction.commit();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
