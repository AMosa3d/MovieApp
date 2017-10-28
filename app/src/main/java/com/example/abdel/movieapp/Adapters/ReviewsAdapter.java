package com.example.abdel.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.abdel.movieapp.Interfaces.DetailManagerInterface;
import com.example.abdel.movieapp.Models.Review;
import com.example.abdel.movieapp.R;

import java.util.List;

/**
 * Created by abdel on 10/24/2017.
 */

public class ReviewsAdapter extends BaseExpandableListAdapter {

    private DetailManagerInterface mDetailManagerInterface;
    private List<Review> reviewsList;
    private Context context;

    public ReviewsAdapter(DetailManagerInterface mDetailManagerInterface,Context context) {
        this.mDetailManagerInterface = mDetailManagerInterface;
        this.context = context;
    }

    public void setReviewList(List<Review> reviewsList) {
        this.reviewsList = reviewsList;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        if(reviewsList == null)
            return 0;
        return reviewsList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;   //only one review per author
    }

    @Override
    public Object getGroup(int groupPosition) {
        return reviewsList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return reviewsList.get(groupPosition).getContent();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = inflater.inflate(R.layout.reviews_list_single_group_item,parent,false);

        TextView textView = (TextView) convertView.findViewById(R.id.review_author);

        textView.setText(reviewsList.get(groupPosition).getAuthor());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = inflater.inflate(R.layout.reviews_list_single_child_item,parent,false);

        TextView textView = (TextView) convertView.findViewById(R.id.review_content);

        textView.setText(reviewsList.get(groupPosition).getContent());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
