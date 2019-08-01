package books.urdu.alihamuh.urdubooks3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;


public class BookMarkArrayAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Integer> page;
    LayoutInflater inflater;
    CommonAppClass Myapp=new CommonAppClass();


    public BookMarkArrayAdapter(Context context,ArrayList<Integer> page) {
        this.context = context;
        this.page=page;
        inflater =(LayoutInflater.from(context));

    }


    @Override
    public int getCount() {
        return page.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder{

        private TextView page;
        private CheckBox chkbx;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();

        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)convertView = vi.inflate(R.layout.bookmark_button,parent,false);

        mViewHolder.page = (TextView)convertView.findViewById(R.id.bookmark_page);
        mViewHolder.chkbx=(CheckBox)convertView.findViewById(R.id.checkbox);


        int code_pg_no=page.get(position);
        int total =46;
        int ShownPageNo=total-code_pg_no+1;

        mViewHolder.page.setText(""+ShownPageNo);



        convertView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
                MainActivity.ITEM =page.get(position);
                context.startActivity(i);
            }
        });

        if(Myapp.getCheckboxShown()){
            mViewHolder.chkbx.setVisibility(View.VISIBLE);
        }


        mViewHolder.chkbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((CheckBox)v).isChecked()){
                    Myapp.getChecks().set(position, 1);
                }
                else{
                    Myapp.getChecks().set(position, 0);
                }
            }
        });

        return convertView;
    }




}


