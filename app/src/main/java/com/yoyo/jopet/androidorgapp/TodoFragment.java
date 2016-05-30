package com.yoyo.jopet.androidorgapp;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class TodoFragment extends ListFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final int IMAGE_SIZE = 96;
    private static final int TEXT_SIZE = 28;
    private static final int DIVIDER_HEIGHT = 5;
    private List<Entry> entries;
    private DialogHelper diaHelper;
    private static DatabaseHelper dbHelper;
    private static ArrayAdapter entryAdapter;
    private ViewPager viewPager;

    public TodoFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TodoFragment newInstance() {
        return new TodoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);
        dbHelper = new DatabaseHelper(getActivity().getApplicationContext());
        entries = dbHelper.getTodoEntries();
        diaHelper = new DialogHelper(getActivity(), getActivity(), dbHelper);
        entryAdapter = new ArrayAdapter(getActivity(), android.R.layout.activity_list_item, android.R.id.text1, entries) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                view.setId(entries.get(position).getId());
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                text1.setText(entries.get(position).getActivity());
                text1.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE);
                ImageView image1 = (ImageView) view.findViewById(android.R.id.icon);
                text1.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                image1.getLayoutParams().height = IMAGE_SIZE;
                image1.getLayoutParams().width = IMAGE_SIZE;
                if (entries.get(position).getStatus() == Entry.TYPE_TODO) {
                    image1.setImageResource(R.drawable.ic_check);
                } else {
                    image1.setImageResource(R.drawable.ic_cross);
                }
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
                diaHelper.changeToNote(dbHelper.getEntry(view.getId()));
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diaHelper.showTodo(dbHelper.getEntry(view.getId()));
            }
        });

        listView.setDivider(new ColorDrawable(getResources().getColor(R.color.colorDivider)));
        listView.setDividerHeight(DIVIDER_HEIGHT);
    }

    public static void updateChanges() {
        entryAdapter.clear();
        entryAdapter.addAll(dbHelper.getTodoEntries());
        entryAdapter.notifyDataSetChanged();
    }
}