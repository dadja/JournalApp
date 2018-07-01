package reptxstudio.journalapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import reptxstudio.journalapp.R;
import reptxstudio.journalapp.database.model.Article;

public class ArticlesAdapter  extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {


//    ArrayList<Article> articles;
    List<Article> articles;
    Onpositionlistener onpositionlistener;


    public ArticlesAdapter(List<Article> articles,  Onpositionlistener onpositionlistener){
        this.articles= articles;
        this.onpositionlistener=onpositionlistener;

    }


    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent,false);

        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {

        holder.binddata(articles.get(position));

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void replaceData(List<Article> articles) {
        this.articles.clear();
        this.articles.addAll(articles);
        notifyDataSetChanged();
    }

    public void addData(List<Article> articles) {
        //for a reason i'm trying to understand i get the data back from firebase but as soon as it comes here nothing happens but using the same method
        // and loading data from other sources like sqlite seems to works i must check that ...
        Log.d("Articles Adapter ","Add the data");
        this.articles.addAll(articles);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.articles.clear();
        notifyDataSetChanged();
    }


    class ArticleViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.content_title) TextView contentText;
        @BindView(R.id.text_title) TextView titleText;
        @BindView(R.id.text_created_time) TextView createdTimeText;
        @BindView(R.id.image_profile) ImageView profileImage;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onpositionlistener.onclick(getAdapterPosition());
                }
            });
        }


        public void binddata(Article article)
        {
            if(!TextUtils.isEmpty(article.getTitle()))
                titleText.setText(article.getTitle());
            if(!TextUtils.isEmpty(article.getTitle()))
                contentText.setText(article.getContenu());
            if(!TextUtils.isEmpty(article.getTitle()))
                createdTimeText.setText(article.getDate());
        }
    }


    public interface  Onpositionlistener{
        void onclick(int position);
    }
}
