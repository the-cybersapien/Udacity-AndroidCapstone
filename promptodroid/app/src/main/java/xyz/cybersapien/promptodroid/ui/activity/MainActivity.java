package xyz.cybersapien.promptodroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.cybersapien.promptodroid.R;
import xyz.cybersapien.promptodroid.data.DataStore;
import xyz.cybersapien.promptodroid.data.model.PromptStory;
import xyz.cybersapien.promptodroid.data.model.User;
import xyz.cybersapien.promptodroid.ui.fragment.PromptEditFragment;
import xyz.cybersapien.promptodroid.ui.fragment.PromptsListFragment;
import xyz.cybersapien.promptodroid.utils.Constants;

public class MainActivity extends AppCompatActivity implements PromptsListFragment.InteractionListener,
        PromptEditFragment.InteractionListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.stories_fragment_container)
    FrameLayout StoryListFragmentContainer;
    @BindView(R.id.story_detail_fragment_container)
    @Nullable
    FrameLayout storyDetailFragmentContainer;

    private boolean isTwoPane;
    private PromptsListFragment promptsListFragment;
    private PromptEditFragment promptEditFragment;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = new User(user);

        isTwoPane = storyDetailFragmentContainer != null;

        if (savedInstanceState == null) {
            promptsListFragment = new PromptsListFragment();
            // Initialize and attach the List Fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.stories_fragment_container, promptsListFragment)
                    .commit();
        }
    }

    private void setEditFragment(@Nullable PromptStory story) {
        promptEditFragment = PromptEditFragment.getInstance(story);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.story_detail_fragment_container, promptEditFragment)
                .commit();
    }

    @Override
    public void addNewPrompt() {
        if (isTwoPane) {
            setEditFragment(null);
        } else {
            // TODO: create Activity for Mobile Phones layout
        }
    }

    @Override
    public void recyclerItemSelected(PromptStory story) {
        if (isTwoPane) {
            setEditFragment(story);
        }
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void saveNewPrompt(PromptStory story) {
        story.setUserId(currentUser.getUid());
        updatePrompt(story);
    }

    @Override
    public void updatePrompt(PromptStory story) {
        DatabaseReference dbRef = DataStore.getInstance().getUserDataReference(currentUser).child(Constants.PROMPT_KEY + "/" + story.getDate());
        dbRef.setValue(story);
        getSupportFragmentManager().beginTransaction()
                .remove(promptEditFragment).commit();
    }

    @Override
    public void startTeleprompt(PromptStory story) {
        Intent prompterIntent = new Intent(MainActivity.this, PromptingActivity.class);
        prompterIntent.putExtra(PromptingActivity.SELECTED_STORY, story);
        startActivity(prompterIntent);
    }
}
