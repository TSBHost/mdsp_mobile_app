package app.wasimappbd.softxmagic.mdspbdnew;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by User on 5/15/2017.
 */

public class Config extends AppCompatActivity {
    public String URL,PageUrl;

    //////// MDSP Database : ref: mdspbd.com///////////
    public Config() {
        this.URL="https://mdspbd.com/mdspbd_app/";
        //this.URL="http://123.200.17.74:2381/mdspbd/";
        //this.URL="http://123.200.17.77/mdspbd/mdspbd_app/";
        //this.URL="http://123.200.17.74:2381/mdspbd/mdspbd_app/";
    }

    public String getURL() {
        return URL;
    }


    ////// Shelter Data Sync ///////////
    public String getShelterUrl() {
        PageUrl = URL+"get_shelter_list.php";
        return PageUrl;
    }

    ////// Shelter Data Sync ///////////
    public String getTaskUrl() {
            PageUrl = URL+"get_majortask.php";
            //PageUrl = URL+"get_tasks_test.php";
        return PageUrl;
    }

    ////// Submission Data Sync ///////////
    public String getSubmissionSyncUrl() {
        PageUrl = URL+"get_submitted_form_list_sync.php";
        return PageUrl;
    }

    ////// Feedback Data Sync ///////////
    public String getFeedbackSyncUrl() {
        //PageUrl = URL+"get_feedback.php";
        PageUrl = "https://mdspbd.com/storage/app/json/";
        return PageUrl;
    }
    ////// Feedback Data Sync ///////////
    public String getAllSync() {
        //PageUrl = URL+"get_feedback.php";
        PageUrl = URL+"get_sync_datea.php";
        return PageUrl;
    }
}
