package books.urdu.alihamuh.urdubooks3;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    public ArrayList<Integer> images;
    private ViewPager viewPager;
    private FragmentStatePagerAdapter adapter;
    //private LinearLayout thumbnailsContainer;
    private int[] resourceIDs;
    CommonAppClass Myapp= new CommonAppClass();


    private ActionMenuView amvMenu;
    int TOTAL_NO_OF_PAGES=47;
    int TOTAL_PAGES_MINUS_ONE=TOTAL_NO_OF_PAGES-1;
    static int ITEM;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        // Find the toolbar view inside the activity layout
        //setting toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);


        amvMenu = (ActionMenuView)toolbar.findViewById(R.id.amvMenu);
        amvMenu.setOnMenuItemClickListener(new ActionMenuView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return onOptionsItemSelected(menuItem);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().hide();

        LinearLayout seekbarLayout=(LinearLayout) findViewById(R.id.seebar_layout);
        seekbarLayout.setVisibility(View.GONE);

        images = new ArrayList<>();

        idCatcher();



        //find view by id
        viewPager = (ViewPager) findViewById(R.id.view_pager);


        setImagesData();


        // init viewpager adapter and attach
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), images);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(ITEM);


        AdView mAdView = findViewById(R.id.page_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        NightModeApplier();


        final AppCompatSeekBar seekBar=(AppCompatSeekBar)findViewById(R.id.pageChangerSeekbar);

        seekBar.setRotationY(180);

        int ShownPageNo=TOTAL_PAGES_MINUS_ONE-viewPager.getCurrentItem()+1;

        seekBar.setProgress(ShownPageNo);
        seekBar.setMax(TOTAL_NO_OF_PAGES);

        final TextView pgTextView =(TextView)findViewById(R.id.pageNumberTextView);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int currentCodePageNO;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                currentCodePageNO=TOTAL_NO_OF_PAGES-progress;
               pgTextView.setText(""+progress+"/"+TOTAL_NO_OF_PAGES);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(currentCodePageNO);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                int ShownPageNoOnPageChange=TOTAL_PAGES_MINUS_ONE-viewPager.getCurrentItem()+1;

                seekBar.setProgress(TOTAL_PAGES_MINUS_ONE-position+1);
               pgTextView.setText(""+ShownPageNoOnPageChange+"/"+TOTAL_NO_OF_PAGES);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // use amvMenu here
        inflater.inflate(R.menu.menu_main, amvMenu.getMenu());
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_night_mode) {

            Myapp.setNightmode(!Myapp.getNightmode());
            //nightMode =!nightMode;
            ITEM =viewPager.getCurrentItem();
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(ITEM);
            if(Myapp.getNightmode()){
                Toast.makeText(this, "night mode on", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "night mode off", Toast.LENGTH_SHORT).show();
            }

        }
        if(id==R.id.action_bookmark){

            Boolean isBookmarked=false;   //default value

            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

            SharedPreferences.Editor editor = settings.edit();

            for(int index =1; index<=settings.getInt("bookmark_no",0);index++){

                int currentPageNo=viewPager.getCurrentItem();
                int bookMarkedPageNo=settings.getInt("bookmark_"+index,0);

                if(currentPageNo==bookMarkedPageNo){
                    isBookmarked =true;
                }else{
                    isBookmarked=false;
                }
            }

            if(isBookmarked) {
                Toast.makeText(this, "already bookmarked", Toast.LENGTH_SHORT).show();
            }else{

                /**bookmark number showing how many total bookmarks are there so that
                 * the total can be used in the for loop for counting.
                 */

                editor.putInt("bookmark_no", settings.getInt("bookmark_no", 0) + 1);
                editor.apply();

                editor.putInt("bookmark_" + settings.getInt("bookmark_no", 0), viewPager.getCurrentItem());
                editor.apply();

                Toast.makeText(this, "bookmarked", Toast.LENGTH_SHORT).show();
            }

        }if(id==R.id.highlight_mode){

            Myapp.setHighlight(!Myapp.getHighlight());
            ITEM =viewPager.getCurrentItem();
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(ITEM);
            if(Myapp.getHighlight()){
                Toast.makeText(this, "highlight mode on", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "highlight mode off", Toast.LENGTH_SHORT).show();
            }
        }



        return super.onOptionsItemSelected(item);
    }



    private View.OnClickListener onClickListener(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 0) {
                    //next page
                    if (viewPager.getCurrentItem() < viewPager.getAdapter().getCount() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                } else {
                    //previous page
                    if (viewPager.getCurrentItem() > 0) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                    }
                }
            }
        };
    }

    private void setImagesData() {
        for (int i = 0; i < resourceIDs.length; i++) {
            images.add(resourceIDs[i]);
        }
    }



    void idCatcher(){

        resourceIDs = new int[TOTAL_NO_OF_PAGES];

        for(int i=0;i<TOTAL_NO_OF_PAGES; i++){

            int index=TOTAL_NO_OF_PAGES-i;
            resourceIDs[i] = getResources().getIdentifier("page_" + index, "drawable", getPackageName());

        }

    }


    @Override
    protected void onStop() {
        super.onStop();


        SharedPreferences pref = getApplicationContext().getSharedPreferences("Page_No", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("resume",viewPager.getCurrentItem());
        editor.apply();

    }


   public void NightModeApplier(){
        SharedPreferences generalSetting= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(generalSetting.getBoolean("nightmode",false)){
            Myapp.setNightmode(true);

            //nightMode =!nightMode;
            ITEM =viewPager.getCurrentItem();
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(ITEM);
            if(Myapp.getNightmode()){
                Toast.makeText(this, "night mode on", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "night mode off", Toast.LENGTH_SHORT).show();
            }
        }else{

            Myapp.setNightmode(false);
        }
   }


}

