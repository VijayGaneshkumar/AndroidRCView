package com.vijay.countrynews.views.itemadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vijay.countrynews.model.NewsItems;
import com.vijay.countrynews.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vijayganeshkumar on 10/09/18.
 */

public class NewsItemAdapter extends  RecyclerView.Adapter<NewsItemAdapter.NewsItemViewHolder> {

    private final String TAG = NewsItemAdapter.class.getSimpleName();

    private ArrayList<NewsItems> dataList;
    private Context mContext;
    private List<NewsItems> newsItemsList;

    public NewsItemAdapter(Context context,List<NewsItems> itemList) {

        this.dataList = new ArrayList<>(itemList.size());
        this.dataList.addAll(itemList);
        this.mContext = context;
    }

    @Override
    public NewsItemAdapter.NewsItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_card_view, viewGroup,false);
        return new NewsItemAdapter.NewsItemViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }

    public String getImageUrl(NewsItems item) {
        if ((item.getRowImageUrl()) == null) return null;
        String url = item.getRowImageUrl();
        return url;
    }

    @Override
    public void onBindViewHolder(NewsItemAdapter.NewsItemViewHolder holder, int position) {
        NewsItemAdapter.NewsItemViewHolder mViewHolder = (NewsItemAdapter.NewsItemViewHolder)holder;
        NewsItems newsItems = dataList.get(position);
        String title = newsItems.getRowTitle();
        String description = newsItems.getRowDescription();
        String imageUrl = getImageUrl(newsItems);

        if(title == null && description == null && imageUrl == null)
            return;

        if(title!= null)
            mViewHolder.newsTitle.setText(title);
        if(description!= null)
            mViewHolder.newsDesc.setText(description);
        if(imageUrl != null) {
            Picasso.get()
                    .load(imageUrl)
                    //.error(R.drawable.blankimage)
                    //.placeholder(R.drawable.blankimage)
                    .into(mViewHolder.newsPhoto);
        }
        else {
            mViewHolder.newsPhoto.setVisibility(View.INVISIBLE);
        }
    }
    public class NewsItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.newsItemTitle)
        TextView newsTitle;
        @BindView(R.id.newsItemDesc)
        TextView newsDesc;
        @BindView(R.id.newsItemPhoto)
        ImageView newsPhoto;
        NewsItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}