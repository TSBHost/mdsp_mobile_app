package app.wasimappbd.softxmagic.mdspbdnew;

import android.provider.BaseColumns;

public class ReviewSubmissionModel {

    private ReviewSubmissionModel(){}
  public static final class ReviewEntry implements BaseColumns {
      // @JsonProperty("totalform")
      public static final String Table = "Submission";
      public static final String id = "id";
      public static final String ShelterCode ="ShelterCode";
      public static final String MajorTasks ="MajorTasks";
      public static final String Tasks ="Tasks";
      public static final String sdate ="SubmisionDate";

    }
}