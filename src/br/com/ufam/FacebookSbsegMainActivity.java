package br.com.ufam;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ufam.R;

import com.facebook.AppEventsLogger;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.LoginButton;
import com.facebook.widget.PickerFragment;
import com.facebook.widget.PlacePickerFragment;
import com.facebook.widget.ProfilePictureView;

public class FacebookSbsegMainActivity extends FragmentActivity {

    private static final String PERMISSION = "publish_actions";
//    private static final Location SEATTLE_LOCATION = new Location("") {
//        {
//            setLatitude(47.6097);
//            setLongitude(-122.3331);
//        }
//    };

    private final String PENDING_ACTION_BUNDLE_KEY = "com.facebook.samples.hellofacebook:PendingAction";

    private Button postStatusUpdateButton;
    private Button postPhotoButton;
    private Button galeryButton;
  //  private Button cameraButton;
    private LoginButton loginButton;
    private ProfilePictureView profilePictureView;
    private TextView greeting;
    private PendingAction pendingAction = PendingAction.NONE;
    private ViewGroup controlsContainer;
    private GraphUser user;
    private GraphPlace place;
    private List<GraphUser> tags;
    private boolean canPresentShareDialog;
    private ImageView imageView1; 
 //   private File caminhoFoto; 
    private Bitmap image = null; 
 //   private String mCurrentPathFile;
    

    
    
    private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
    }
    private UiLifecycleHelper uiHelper;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
            Log.d("SBSegapp", String.format("Error: %s", error.toString()));
        }

        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
            Log.d("SBSegApp", "Success!");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            String name = savedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);
            pendingAction = PendingAction.valueOf(name);
        }

        setContentView(R.layout.activity_facebook_sbseg_main);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                FacebookSbsegMainActivity.this.user = user;
                updateUI();
                // It's possible that we were waiting for this.user to be populated in order to post a
                // status update.
                handlePendingAction();
            }
        });

        profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
        greeting = (TextView) findViewById(R.id.greeting);

        postStatusUpdateButton = (Button) findViewById(R.id.postStatusUpdateButton);
        postStatusUpdateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickPostStatusUpdate();
            }
        });

        postPhotoButton = (Button) findViewById(R.id.postPhotoButton);
        postPhotoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickPostPhoto();
            }
        });
        
        imageView1 = (ImageView)findViewById(R.id.imageView1);
        galeryButton = (Button)findViewById(R.id.idBuscarImagem);
      //s  cameraButton = (Button)findViewById(R.id.idImagemCamera);
//        pickFriendsButton = (Button) findViewById(R.id.idBuscarImagem);
//        pickFriendsButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                onClickPickFriends();
//            }
//        });

//        pickPlaceButton = (Button) findViewById(R.id.pickPlaceButton);
//        pickPlaceButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                onClickPickPlace();
//            }
//        });

        controlsContainer = (ViewGroup) findViewById(R.id.main_ui_container);

        final FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment != null) {
            // If we're being re-created and have a fragment, we need to a) hide the main UI controls and
            // b) hook up its listeners again.
            controlsContainer.setVisibility(View.GONE);
            if (fragment instanceof FriendPickerFragment) {
                setFriendPickerListeners((FriendPickerFragment) fragment);
            } else if (fragment instanceof PlacePickerFragment) {
                setPlacePickerListeners((PlacePickerFragment) fragment);
            }
        }

        // Listen for changes in the back stack so we know if a fragment got popped off because the user
        // clicked the back button.
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (fm.getBackStackEntryCount() == 0) {
                    // We need to re-show our UI.
                    controlsContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        canPresentShareDialog = FacebookDialog.canPresentShareDialog(this,
                FacebookDialog.ShareDialogFeature.SHARE_DIALOG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();

        // Call the 'activateApp' method to log an app event for use in analytics and advertising reporting.  Do so in
        // the onResume methods of the primary Activities that an app may be launched into.
        AppEventsLogger.activateApp(this);

        updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);

        outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (cont == 0) { 
//        	// A API do Facebook exige essa chamada para 
//        	// concluir o processo de login. 
//        uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
//        } else { 
        
       
            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                    // Image captured and saved to fileUri specified in the Intent
                	handleSmallCameraPhoto(data);
//                	galleryAddPic();
//                    Toast.makeText(this, "Image saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the image capture
                } else {
                    // Image capture failed, advise user
                }
            }
           else if(requestCode == 0){
	        		if(resultCode == RESULT_OK) {  
	        				Uri selectedImage = data.getData(); 
	        				String[] filePathColumn = { MediaStore.Images.Media.DATA }; 
	        				Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null); 
	        				cursor.moveToFirst(); 
	        				int columnIndex = cursor.getColumnIndex(filePathColumn[0]); 
	        				String filePath = cursor.getString(columnIndex); // file // path // of // selected // image 
	        				cursor.close(); 
	        				Bitmap yourSelectedImage = BitmapFactory .decodeFile(filePath); 
	        				imageView1.setImageBitmap(yourSelectedImage); 
	        				image = yourSelectedImage;
	        		}else if (resultCode == RESULT_CANCELED) {
	                    // User cancelled the image capture
	                } else {
	                    // Image capture failed, advise user
	                }
        		 
//        				Uri selectedFoto = data.getData(); 
//        				String[] filePathColumnPhoto = { MediaStore.Images.Media.DATA };
//						galleryAddPic(caminhoFoto);
//        				Cursor cursorPhoto = getContentResolver().query(selectedFoto, filePathColumnPhoto, null, null, null); 
//        				cursorPhoto.moveToFirst(); 
//        				int columnIndexPhoto = cursorPhoto.getColumnIndex(filePathColumnPhoto[0]); 
//        				String filePathPhoto = cursorPhoto.getString(columnIndexPhoto); // file // path // of // selected // image 
//        				cursorPhoto.close(); 
//        				Bitmap yourPhotoCaptured = BitmapFactory .decodeFile(filePathPhoto); 
//        				imageView1.setImageBitmap(yourPhotoCaptured); 
//        				image = yourPhotoCaptured;
//        			        if (resultCode == RESULT_OK) {
//        			            // Image captured and saved to fileUri specified in the Intent
//        			            Toast.makeText(this, "Image saved to:\n" +
//        			                     data.getData(), Toast.LENGTH_LONG).show();
//        			        } else if (resultCode == RESULT_CANCELED) {
//        			            // User cancelled the image capture
//        			        } else {
//        			            // Image capture failed, advise user
//        			        }
//        			    }
//        				handleSmallCameraPhoto(takePictureIntent);
        		}  
        	else{
        		uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
        	}
    }

    

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (pendingAction != PendingAction.NONE &&
                (exception instanceof FacebookOperationCanceledException ||
                exception instanceof FacebookAuthorizationException)) {
                new AlertDialog.Builder(FacebookSbsegMainActivity.this)
                    .setTitle(R.string.cancelled)
                    .setMessage(R.string.permission_not_granted)
                    .setPositiveButton(R.string.ok, null)
                    .show();
            pendingAction = PendingAction.NONE;
        } else if (state == SessionState.OPENED_TOKEN_UPDATED) {
            handlePendingAction();
        }
        updateUI();
    }

    private void updateUI() {
        Session session = Session.getActiveSession();
        boolean enableButtons = (session != null && session.isOpened());

        postStatusUpdateButton.setEnabled(enableButtons/* || canPresentShareDialog*/);
        postPhotoButton.setEnabled(enableButtons);
        galeryButton.setEnabled(enableButtons);
        //cameraButton.setEnabled(enableButtons);

        if (enableButtons && user != null) {
            profilePictureView.setProfileId(user.getId());
            greeting.setText(getString(R.string.hello_user, user.getFirstName()));
        } else {
            profilePictureView.setProfileId(null);
            greeting.setText(null);
        }
    }

    @SuppressWarnings("incomplete-switch")
    private void handlePendingAction() {
        PendingAction previouslyPendingAction = pendingAction;
        // These actions may re-set pendingAction if they are still pending, but we assume they
        // will succeed.
        pendingAction = PendingAction.NONE;

        switch (previouslyPendingAction) {
            case POST_PHOTO:
                postPhoto();
                break;
            case POST_STATUS_UPDATE:
                postStatusUpdate();
                break;
        }
    }

    private interface GraphObjectWithId extends GraphObject {
        String getId();
    }

    private void showPublishResult(String message, GraphObject result, FacebookRequestError error) {
        String title = null;
        String alertMessage = null;
        if (error == null) {
            title = getString(R.string.success);
            String id = result.cast(GraphObjectWithId.class).getId();
            alertMessage = getString(R.string.successfully_posted_post, message, id);
        } else {
            title = getString(R.string.error);
            alertMessage = error.getErrorMessage();
        }

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(alertMessage)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    private void onClickPostStatusUpdate() {
        performPublish(PendingAction.POST_STATUS_UPDATE, canPresentShareDialog);
    }

    private FacebookDialog.ShareDialogBuilder createShareDialogBuilder() {
        return new FacebookDialog.ShareDialogBuilder(this)
                .setName("SBseg ")
                .setDescription("Simpósio Brasileiro de Segurança")
                .setLink("sbseg2013.icomp.ufam.edu.br");
    }

    private void postStatusUpdate() {
        if (canPresentShareDialog) {
            FacebookDialog shareDialog = createShareDialogBuilder().build();
            uiHelper.trackPendingDialogCall(shareDialog.present());
        } else if (user != null && hasPublishPermission()) {
            final String message = getString(R.string.status_update, user.getFirstName(), (new Date().toString()));
            Request request = Request.newStatusUpdateRequest(Session.getActiveSession(), message, place, tags, new Request.Callback() {
                        @Override
                        public void onCompleted(Response response) {
                            showPublishResult(message, response.getGraphObject(), response.getError());
                        }
                    });
            request.executeAsync();
        } else {
            pendingAction = PendingAction.POST_STATUS_UPDATE;
        }
    }

    private void onClickPostPhoto() {
        performPublish(PendingAction.POST_PHOTO, false);
    }

    private void postPhoto() {
        if (hasPublishPermission()) {
            Bitmap imagePost = this.image;//BitmapFactory.decodeResource(this.getResources(), R.drawable.icon);
            if(imagePost==null){
            	Toast.makeText(this, "Insira uma imagem", Toast.LENGTH_LONG).show();
            }
//            ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
//            imagePost.compress(Bitmap.CompressFormat.PNG, 100, baos); 
//            @SuppressWarnings("unused")
//			byte[] bytes = baos.toByteArray();
            Request request = Request.newUploadPhotoRequest(Session.getActiveSession(), imagePost, new Request.Callback() {
                @Override
                public void onCompleted(Response response) {
                    showPublishResult(getString(R.string.photo_post), response.getGraphObject(), response.getError());
                }
            });
            request.executeAsync();
        } else {
            pendingAction = PendingAction.POST_PHOTO;
        }
        
        imageView1.setImageResource(R.drawable.face_batman);
        this.image = null;
    }
    
    public void galleryButtonClick(View v) { 
    	// apagaTemp(); 
    	int actionCode = 0;
    	Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
    	intent.setType("image/*"); 
    	startActivityForResult(intent, actionCode); 
    //	this.cont++; 
    }
    
    public void cameraButtonClick(View v) throws IOException{
//    	File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//    	caminhoFoto = new File(picsDir, "foto.jpg");		
//    	Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//    	i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(caminhoFoto));	
//    	startActivityForResult(i, 1);
    	
//    	takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);   
//      startActivityForResult(takePictureIntent, 1);
    	int actionCode = 1;
    	dispatchTakePictureIntent(actionCode);

    }
    
    private void handleSmallCameraPhoto(Intent intent) {
        Bundle extras = intent.getExtras();
        this.image = (Bitmap) extras.get("data");
        imageView1.setImageBitmap(image);
    }
    
    public void dispatchTakePictureIntent(int actionCode) throws IOException {
//    	File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//    	caminhoFoto = new File(picsDir, "foto.jpg");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(caminhoFoto));	
        startActivityForResult(takePictureIntent, actionCode);
    }
    
//    @SuppressLint("SimpleDateFormat")
//	private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "foto" + timeStamp + "_";
//        File image = File.createTempFile(
//            imageFileName, 
//            ".jpg", 
//            getAlbumDir()
//        );
//        mCurrentPathFile = image.getAbsolutePath();
//        return image;
//    }

    
    @SuppressWarnings("unused")
	private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = getAlbumDir();// new File(mCurrentPathFile);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    private File getAlbumDir(){
    	File storageDir = new File(
    		    Environment.getExternalStoragePublicDirectory(
    		        Environment.DIRECTORY_PICTURES
    		    ), 
    		    getAlbumName()
    		);    
    	return storageDir;
    	
    }
    
    private String getAlbumName() {
        return getString(R.string.album_name);
    }
    	   

//    private void showPickerFragment(PickerFragment<?> fragment) {
//        fragment.setOnErrorListener(new PickerFragment.OnErrorListener() {
//            @Override
//            public void onError(PickerFragment<?> pickerFragment, FacebookException error) {
//                String text = getString(R.string.exception, error.getMessage());
//                Toast toast = Toast.makeText(FacebookSbsegMainActivity.this, text, Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });
//
//        FragmentManager fm = getSupportFragmentManager();
//        fm.beginTransaction()
//                .replace(R.id.fragment_container, fragment)
//                .addToBackStack(null)
//                .commit();
//
//        controlsContainer.setVisibility(View.GONE);
//
//        // We want the fragment fully created so we can use it immediately.
//        fm.executePendingTransactions();
//
//        fragment.loadData(false);
//    }

//    private void onClickPickFriends() {
//        final FriendPickerFragment fragment = new FriendPickerFragment();
//
//        setFriendPickerListeners(fragment);
//
//        showPickerFragment(fragment);
//    }

    private void setFriendPickerListeners(final FriendPickerFragment fragment) {
        fragment.setOnDoneButtonClickedListener(new FriendPickerFragment.OnDoneButtonClickedListener() {
            @Override
            public void onDoneButtonClicked(PickerFragment<?> pickerFragment) {
                onFriendPickerDone(fragment);
            }
        });
    }

    private void onFriendPickerDone(FriendPickerFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();

        String results = "";

        List<GraphUser> selection = fragment.getSelection();
        tags = selection;
        if (selection != null && selection.size() > 0) {
            ArrayList<String> names = new ArrayList<String>();
            for (GraphUser user : selection) {
                names.add(user.getName());
            }
            results = TextUtils.join(", ", names);
        } else {
            results = getString(R.string.no_friends_selected);
        }

        showAlert(getString(R.string.you_picked), results);
    }

    private void onPlacePickerDone(PlacePickerFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();

        String result = "";

        GraphPlace selection = fragment.getSelection();
        if (selection != null) {
            result = selection.getName();
        } else {
            result = getString(R.string.no_place_selected);
        }

        place = selection;

        showAlert(getString(R.string.you_picked), result);
    }

//    private void onClickPickPlace() {
//        final PlacePickerFragment fragment = new PlacePickerFragment();
//        fragment.setLocation(SEATTLE_LOCATION);
//        fragment.setTitleText(getString(R.string.pick_seattle_place));
//
//        setPlacePickerListeners(fragment);
//
//        showPickerFragment(fragment);
//    }

    private void setPlacePickerListeners(final PlacePickerFragment fragment) {
        fragment.setOnDoneButtonClickedListener(new PlacePickerFragment.OnDoneButtonClickedListener() {
            @Override
            public void onDoneButtonClicked(PickerFragment<?> pickerFragment) {
                onPlacePickerDone(fragment);
            }
        });
        fragment.setOnSelectionChangedListener(new PlacePickerFragment.OnSelectionChangedListener() {
            @Override
            public void onSelectionChanged(PickerFragment<?> pickerFragment) {
                if (fragment.getSelection() != null) {
                    onPlacePickerDone(fragment);
                }
            }
        });
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    private boolean hasPublishPermission() {
        Session session = Session.getActiveSession();
        return session != null && session.getPermissions().contains("publish_actions");
    }

    private void performPublish(PendingAction action, boolean allowNoSession) {
        Session session = Session.getActiveSession();
        if (session != null) {
            pendingAction = action;
            if (hasPublishPermission()) {
                // We can do the action right away.
                handlePendingAction();
                return;
            } else if (session.isOpened()) {
                // We need to get new permissions, then complete the action when we get called back.
                session.requestNewPublishPermissions(new Session.NewPermissionsRequest(this, PERMISSION));
                return;
            }
        }

        if (allowNoSession) {
            pendingAction = action;
            handlePendingAction();
        }
    }
}
