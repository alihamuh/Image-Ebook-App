package books.urdu.alihamuh.urdubooks3;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

public class CommonAppClass extends Application {

    private static Context context;
    private static volatile Boolean nightmode =false;
    private static volatile Boolean highlight=false;
    private static volatile Boolean checkboxShown=false;
    private ArrayList<Integer> checks=new ArrayList<Integer>();

    public void onCreate() {
        super.onCreate();
        CommonAppClass.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return CommonAppClass.context;
    }


    public Boolean getNightmode(){
        return this.nightmode;
    }

    public void setNightmode(Boolean nightmodeChanged){
        nightmode=nightmodeChanged;
    }

    public Boolean getHighlight(){
        return this.highlight;
    }

    public void setHighlight(Boolean highlightChanged){
        highlight=highlightChanged;
    }

    public Boolean getCheckboxShown() {
        return this.checkboxShown;
    }

    public void setCheckboxShown(Boolean checkboxShownChanged){
        checkboxShown=checkboxShownChanged;
    }

    public ArrayList<Integer> getChecks(){
        return this.checks;
    }
    public void setChecks(ArrayList<Integer> checksChanged){
        checks=checksChanged;
    }
}
