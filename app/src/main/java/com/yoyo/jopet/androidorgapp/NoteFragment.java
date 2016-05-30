package com.yoyo.jopet.androidorgapp;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class NoteFragment extends ListFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final int MAX_LENGTH = 50;
    private static final int TRUNC_LENGTH = 40;
    private static final int START_INDEX = 0;
    private static final int DIVIDER_HEIGHT = 5;
    private List<Entry> entries;
    private DialogHelper diaHelper;
    private static DatabaseHelper dbHelper;
    private static ArrayAdapter entryAdapter;

    public NoteFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static NoteFragment newInstance() {
        return new NoteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note, container, false);
        dbHelper = new DatabaseHelper(getActivity().getApplicationContext());
        entries = dbHelper.getNoteEntries();
        diaHelper = new DialogHelper(getActivity(), getActivity(), dbHelper);
        entryAdapter = new ArrayAdapter(getActivity(), android.R.layout.two_line_list_item, android.R.id.text1, entries) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                view.setId(entries.get(position).getId());
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(entries.get(position).getTitle().toUpperCase());
                text2.setText(shortenString(entries.get(position).getContent()));
                text1.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                text2.setTextColor(getResources().getColor(R.color.colorSecondaryText));
                return view;
            }
        };
        setListAdapter(entryAdapter);
        rootView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView = getListView();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                diaHelper.changeToTodo(dbHelper.getEntry(view.getId()));
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diaHelper.showNote(dbHelper.getEntry(view.getId()));
            }
        });

        listView.setDivider(new ColorDrawable(getResources().getColor(R.color.colorDivider)));
        listView.setDividerHeight(DIVIDER_HEIGHT);
    }

    private String shortenString(String string) {
        if (string != null) {
            if (string.length() > MAX_LENGTH) {
                return (truncate(string, TRUNC_LENGTH) + "...");
            } else {
                return string;
            }
        } else {
            return "";
        }
    }

    private String truncate(final String content, final int lastIndex) {
        String result = content.substring(START_INDEX, lastIndex);
        if (content.charAt(lastIndex) != ' ') {
            result = result.substring(START_INDEX, result.lastIndexOf(" "));
        }
        return result;
    }

    public static void updateChanges() {
        entryAdapter.clear();
        entryAdapter.addAll(dbHelper.getNoteEntries());
        entryAdapter.notifyDataSetChanged();
    }
}