package android.example.hartshornscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.example.hartshornscheduler.Entity.TermEntity;
import android.example.hartshornscheduler.R;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;
        private final TextView termItemView2;
        private final TextView termItemView3;


        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.termTextView);
            termItemView2 = itemView.findViewById(R.id.termTextView2);
            termItemView3 = itemView.findViewById(R.id.termTextView3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final TermEntity current = mTerms.get(position);
                    Intent intent = new Intent(context, CourseActivity.class);
                    intent.putExtra("termTitle", current.getTermTitle());
                    intent.putExtra("termStartDate", current.getTermStartDate());
                    intent.putExtra("termEndDate", current.getTermEndDate());
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<TermEntity> mTerms; // Cached copy of words

    public TermAdapter(Context context) {
        //Log.d("TermA", "at TermAdapter" + mTerms);
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.d("TermA", "at onCreateViewHolder" + mTerms);
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {
        //Log.d("TermA", "at onBindView Holder" + mTerms);

        if (mTerms != null) {
            final TermEntity current = mTerms.get(position);
             current.toString();
            holder.termItemView.setText(current.getTermTitle());
            holder.termItemView2.setText(current.getTermStartDate());
            holder.termItemView3.setText(current.getTermEndDate());

        } else {
            //Covers the case of data not being ready yet.
            holder.termItemView.setText("No Terms");
        }
    }

    @Override
    public int getItemCount() {
        //Log.d("TermA", "at Get ItemCount" + mTerms);

        if (mTerms != null)
            return mTerms.size();
        else return 1;
    }


    public void setTerms(List<TermEntity> terms) {
        //Log.d("TermA", "at setWords" + words);
        mTerms = terms;
        notifyDataSetChanged();
    }
    
}
