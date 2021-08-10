package android.example.hartshornscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.example.hartshornscheduler.Entity.NoteEntity;
import android.example.hartshornscheduler.R;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteItemView;
        private final TextView noteItemView2;


        private NoteViewHolder(View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.noteTextView);
            noteItemView2 = itemView.findViewById(R.id.noteTextView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final NoteEntity current = mNotes.get(position);
                    Intent intent = new Intent(context, NoteDetail.class);
                    intent.putExtra("noteTitle", current.getNoteTitle());
                    intent.putExtra("noteDetails", current.getNoteDetails());
                    intent.putExtra("position",position);
                    intent.putExtra("noteID",current.getNoteID());
                    intent.putExtra("courseID", current.getCourseID());
                    context.startActivity(intent);
                }
            });
        }

    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<NoteEntity> mNotes; // Cached copy of words

    public NoteAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.note_list_item, parent, false);

        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        if (mNotes != null) {
            NoteEntity current = mNotes.get(position);
            holder.noteItemView.setText(Integer.toString(current.getCourseID()));
            holder.noteItemView2.setText(current.getNoteTitle());
        } else {
            // Covers the case of data not being ready yet.
            holder.noteItemView.setText("No Word");
            holder.noteItemView2.setText("No Word");
        }

    }

    public void setWords(List<NoteEntity> words) {
        Log.d("Note Adapter", "at setWords Size: "+words.size() );
        mNotes = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }
}