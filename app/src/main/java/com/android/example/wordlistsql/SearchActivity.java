package com.android.example.wordlistsql;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = EditWordActivity.class.getSimpleName();
    private TextView mTextView;
    private EditText mEditWordView;
    private WordListOpenHelper mDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mEditWordView = ((EditText) findViewById(R.id.search_word));
        mTextView = ((TextView) findViewById(R.id.search_result));
        mDB = new WordListOpenHelper(this);
    }

    public void showResult(View view) {
        String word = mEditWordView.getText().toString();
        mTextView.setText("Search term: '" + word + "'\n\n");

        Cursor cursor = mDB.search(word);

        if (cursor != null & cursor.getCount() > 0) {
            cursor.moveToFirst();
            int index;
            String result;
            // Iterate over the cursor, while there are entries.
            do {
                // Don't guess at the column index. Get the index for the named column.
                index = cursor.getColumnIndex(WordListOpenHelper.KEY_WORD);
                // Get the value from the column for the current cursor.
                result = cursor.getString(index);
                // Add result to what's already in the text view.
                mTextView.append(result + "\n");
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            mTextView.append(getString(R.string.no_result));
        }
    }
}