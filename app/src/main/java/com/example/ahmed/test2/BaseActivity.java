package com.example.ahmed.test2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


public class BaseActivity extends AppCompatActivity
{

    /* UI */
    protected Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    /**
     * sets up the navigation drawer in the actibity
     *
     * @param toolbar the activitie's toolbar, to add a burger icon
     */
    protected Drawer setupNavigationBar(final BaseActivity activity, Toolbar toolbar)
    {
        // find the id of that activity
        int currentActivityId = -1;
        if (activity.getClass().equals(UserProfileActivity.class))
            currentActivityId = 1;
        else if (activity.getClass().equals(SearchActivity.class))
            currentActivityId = 2;
        final int finalCurrentActivityId = currentActivityId;


        // profile header
        String userName = "" + " " + "";
        final ProfileDrawerItem userProfile = new ProfileDrawerItem().withName(userName).
                withEmail("");
        final AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(activity)
                .addProfiles(userProfile)
                .withAlternativeProfileHeaderSwitching(false)
                .withCompactStyle(true)
                .build();



        // build navigation drawer
        DrawerBuilder builder = new DrawerBuilder()
                .withActivity(activity)
                .withAccountHeader(accountHeader)
                .withToolbar(toolbar);
        builder.addDrawerItems(new PrimaryDrawerItem().withIdentifier(1).withName("DashBoard").withIcon(R.drawable.ic_menu_black_48dp)
                , new PrimaryDrawerItem().withIdentifier(2).withName("My Profile").withIcon(R.drawable.ic_face_black_48dp)
                , new PrimaryDrawerItem().withIdentifier(3).withName("Find User").withIcon(R.drawable.ic_search_black_48dp)
                , new PrimaryDrawerItem().withIdentifier(4).withName("Messages").withIcon(R.drawable.ic_chat_bubble_outline_black_48dp)
                , new PrimaryDrawerItem().withIdentifier(5).withName("Communities").withIcon(R.drawable.ic_people_outline_black_48dp)
                , new PrimaryDrawerItem().withIdentifier(6).withName("My Rides").withIcon(R.drawable.ic_directions_car_black_24dp)
                , new PrimaryDrawerItem().withIdentifier(7).withName("Notifications").withIcon(R.drawable.ic_notifications_none_black_24dp)
                , new PrimaryDrawerItem().withIdentifier(8).withName("Contact Us").withIcon(R.drawable.ic_mail_outline_black_24dp)
                , new DividerDrawerItem()
                , new PrimaryDrawerItem().withIdentifier(10).withName("Log Out").withIcon(R.drawable.ic_exit_to_app_black_48dp));

        builder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener()
        {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem)
            {
                // if clicked on the same activity we are on do nothing
                int id = drawerItem.getIdentifier();
                if (id == finalCurrentActivityId)
                    return true;

                // launch another activity
                if (id < 10)
                {
                    // select the activity to launch
                    final Intent intent = new Intent();
                    if (id == 2)
                    {
                        intent.setClass(activity, UserProfileActivity.class);
                        intent.putExtra(Constants.USER_ID, 1);
                    } else if (id == 3)
                    {
                        intent.setClass(activity, SearchActivity.class);
                    }


                    // launch the activity after some milliseconds to show the drawer close animation
                    drawer.closeDrawer();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            activity.startActivity(intent);
                            drawer.setSelection(1, false);
                        }
                    }, 500);

                } else if (id == 10)
                {

                    Intent intent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }

                return true;
            }
        });

        // select the current activity
        drawer = builder.build();
        if (currentActivityId > 1)
            drawer.setSelection(currentActivityId);
        return drawer;
    }

    protected void closeKeyboard()
    {
        View view = this.getCurrentFocus();
        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed()
    {

        if (drawer != null && drawer.isDrawerOpen())
            drawer.closeDrawer();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
