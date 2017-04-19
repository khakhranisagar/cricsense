package sagarkhakhrani.cricsense.FirebaseClient;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sagar.khakhrani on 27-03-2017.
 */

public class FirebaseInstance {
    private static FirebaseInstance firebaseInstance;
    private static DatabaseReference databaseReference;
    private FirebaseInstance(){

    }

    public static FirebaseInstance getFirebaseInstance(){
        if(firebaseInstance==null){

            firebaseInstance=new FirebaseInstance();
        }
        return firebaseInstance;

    }
    public static DatabaseReference getDatabaseReference(){
        if(databaseReference==null)
        {

            databaseReference = FirebaseDatabase.getInstance().getReference();

        }
        return databaseReference;
    }
}
