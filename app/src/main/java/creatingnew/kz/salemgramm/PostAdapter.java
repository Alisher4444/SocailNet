package creatingnew.kz.salemgramm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Алишер on 19.06.2016.
 */
public class PostAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private List<Post> posts;
    private Context context;
    private List<Profile> profiles;

    private LayoutInflater inflater;

    public PostAdapter(List<Post> posts, Context context,List<Profile> profiles) {
        this.posts = posts;
        this.context = context;
        this.profiles = profiles;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {

        HeaderViewHOlder viewHodler=null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_header_item,parent,false);
            viewHodler = new HeaderViewHOlder(convertView);
            convertView.setTag(viewHodler);
        }else {
            viewHodler = (HeaderViewHOlder) convertView.getTag();
        }

        viewHodler.usernameTextView.setText(posts.get(position).getUser().getProperty("name").toString());
        Glide.with(context).load(profiles.get(position).getImage()).centerCrop()
                                                            .into(viewHodler.profileImageView);

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder viewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_post_item,parent,false);
            viewHolder = new ItemViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ItemViewHolder) convertView.getTag();
        }

        viewHolder.messageTextView.setText(posts.get(position).getMessage());

        Glide.with(context).load(posts.get(position).getFile())
                        .centerCrop().into(viewHolder.postImageView);

        return convertView;
    }


    private class ItemViewHolder{
        ImageView postImageView;
        TextView messageTextView;


        public ItemViewHolder(View v){
            messageTextView = (TextView) v.findViewById(R.id.messageTextView);
            postImageView = (ImageView) v.findViewById(R.id.postImageView);


        }
    }

    private class HeaderViewHOlder{
        TextView usernameTextView;
        ImageView profileImageView;

        public HeaderViewHOlder(View v){

            usernameTextView = (TextView) v.findViewById(R.id.usernameTextView);
            profileImageView = (ImageView) v.findViewById(R.id.profileImageView);

        }
    }

}
