package com.mvp.using.java.ui.dialog;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.badasoftware.library.customdialog.DynamicDialog;
import com.google.android.material.button.MaterialButton;
import com.mvp.using.java.R;

public class ShowAlertDialogActivity extends AppCompatActivity {

    private static final String TAG = ShowAlertDialogActivity.class.getSimpleName();

    private MaterialButton showDialogMaterialButton;
    private DynamicDialog dynamicDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_alert_dialog);
        initializeView();
        initializeObject();
        setOnClickListener();
    }

    protected void initializeView() {
        showDialogMaterialButton = findViewById(R.id.showDialogMaterialButton);
    }

    protected void initializeObject() {


    }

    protected void setOnClickListener() {
        showDialogMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicDialogFullScreen();
            }
        });
    }

    private void dynamicDialogOne() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.neutralButtonTextView:
                        dynamicDialog.dismiss();
                        Toast.makeText(ShowAlertDialogActivity.this, "Neutral", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.negativeButtonTextView:
                        dynamicDialog.dismiss();
                        Toast.makeText(ShowAlertDialogActivity.this, "Negative", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.positiveButtonTextView:
                        dynamicDialog.dismiss();
                        Toast.makeText(ShowAlertDialogActivity.this, "Positive", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        dynamicDialog = new DynamicDialog.Builder(this)
                .setView(R.layout.test_custom_dialog)
                .setWindowAnimationStyle(R.style.EnterTopExitBottomAnimation)
                .setGravity(Gravity.CENTER)
                .setCanceledOnTouchOutside(false)
                .setOnClickListener(listener, R.id.neutralButtonTextView, R.id.negativeButtonTextView, R.id.positiveButtonTextView)
                .applyAttribute(true)
                .build();

        dynamicDialog.show();
    }

    private void dynamicDialogTwo() {
        dynamicDialog = new DynamicDialog.Builder(this)
                .setView(R.layout.test_custom_dialog)
                .setTheme(R.style.Dynamic_Dialog_Default_Style)
                .setWindowAnimationStyle(R.style.EnterLeftExitRightAnimation)
                .setPadding(20, 0, 20, 0)
                .setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setGravity(Gravity.CENTER)
                .setCanceledOnTouchOutside(true)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(ShowAlertDialogActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Toast.makeText(ShowAlertDialogActivity.this, "Dismiss", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                            /*
                             * If return true then click on back dialog is not dismiss
                             * If return false then click on back dialog is dismiss
                             */
                            return true;
                        } else {
                            return false;
                        }
                    }
                })
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ShowAlertDialogActivity.this, "Neutral", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }, R.id.neutralButtonTextView)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ShowAlertDialogActivity.this, "Negative", Toast.LENGTH_SHORT).show();
                        cancel();
                    }
                }, R.id.negativeButtonTextView)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ShowAlertDialogActivity.this, "Positive", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }, R.id.positiveButtonTextView)
                .setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(ShowAlertDialogActivity.this, "Positive Long", Toast.LENGTH_SHORT).show();
                        dismiss();
                        return false;
                    }
                }, R.id.positiveButtonTextView)
                .applyAttribute(false)
                .build();

        ImageView icon                  = dynamicDialog.findView(R.id.iconImageView);
        TextView title                  = dynamicDialog.findView(R.id.titleTextView);
        TextView message                = dynamicDialog.findView(R.id.messageTextView);
        TextView neutralButtonTextView  = dynamicDialog.findView(R.id.neutralButtonTextView);
        TextView negativeButtonTextView = dynamicDialog.findView(R.id.negativeButtonTextView);
        TextView positiveButtonTextView = dynamicDialog.findView(R.id.positiveButtonTextView);

        icon.setImageResource(R.drawable.ic_question_mark);
        title.setText("Confirm Exit");
        message.setText("Are you sure you want to exit?");

        neutralButtonTextView.setText("Cancel");
        negativeButtonTextView.setText(" No ");
        positiveButtonTextView.setText(" Yes ");
        dynamicDialog.show();
    }

    private void dynamicDialogFullScreen() {
        dynamicDialog = new DynamicDialog.Builder(this)
                .setView(R.layout.test_custom_dialog)
                .setTheme(R.style.Dynamic_Dialog_Default_Style)
                .setWindowAnimationStyle(R.style.BottomSheetAnimation)
                .setPadding(0, 0, 0, 0)
                .setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                .setGravity(Gravity.CENTER)
                .setCanceledOnTouchOutside(true)
                .applyAttribute(true)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(ShowAlertDialogActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Toast.makeText(ShowAlertDialogActivity.this, "Dismiss", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                            /*
                             * If return true then click on back dialog is not dismiss
                             * If return false then click on back dialog is dismiss
                             */
                            return true;
                        } else {
                            return false;
                        }
                    }
                })
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ShowAlertDialogActivity.this, "Neutral", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }, R.id.neutralButtonTextView)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ShowAlertDialogActivity.this, "Negative", Toast.LENGTH_SHORT).show();
                        cancel();
                    }
                }, R.id.negativeButtonTextView)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ShowAlertDialogActivity.this, "Positive", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }, R.id.positiveButtonTextView)
                .setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(ShowAlertDialogActivity.this, "Positive Long", Toast.LENGTH_SHORT).show();
                        dismiss();
                        return false;
                    }
                }, R.id.positiveButtonTextView)
                .build();

        ImageView icon                  = dynamicDialog.findView(R.id.iconImageView);
        TextView title                  = dynamicDialog.findView(R.id.titleTextView);
        TextView message                = dynamicDialog.findView(R.id.messageTextView);
        TextView neutralButtonTextView  = dynamicDialog.findView(R.id.neutralButtonTextView);
        TextView negativeButtonTextView = dynamicDialog.findView(R.id.negativeButtonTextView);
        TextView positiveButtonTextView = dynamicDialog.findView(R.id.positiveButtonTextView);

        icon.setImageResource(R.drawable.ic_question_mark);
        title.setText("Confirm Exit");
        message.setText("Are you sure you want to exit?");

        neutralButtonTextView.setText("Cancel");
        negativeButtonTextView.setText(" No ");
        positiveButtonTextView.setText(" Yes ");
        dynamicDialog.show();
    }

    public void dismiss() {
        if (dynamicDialog != null && dynamicDialog.isShowing()) {
            dynamicDialog.dismiss();
        }
    }

    public void cancel() {
        if (dynamicDialog != null && dynamicDialog.isShowing()) {
            dynamicDialog.cancel();
        }
    }
}