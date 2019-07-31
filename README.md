# Welcome to Android_Date-and-TimePicker !

วันนี้ขอเสนอการสร้าง **Date-picker** และ **Time-picker** ใน Fragment

# Date - Picker

# Step 1

สร้าง Fragment และ Layout ของ Fragment สำหรับคนที่สร้างเป็นแล้วสามารถข้ามไป Step 2 ได้เลย

ไปที่ Folder ที่ MainActivity อยู่ หลังจากนั้นคลิกขวา New ต่อด้วย Java Class จะมีหน้าต่างโผล่ขึ้นมาให้ตั้งค่า

ตั้งตามนี้เลย
![Image](https://drive.google.com/uc?id=1DbD2dJoNSS-t5Dnd_jjIwrSwxH4Ai-T_)

ตรงส่วน Subclass ให้พิมพ์คำว่า **Fragment** แล้วเลือกตามรูปเลย

### กด OK !!!

หลังจากนั้นก็ทำการสร้าง Layout ให้กับ Fragment1 โดยไปที่ Folder Layout คลิกขวา New ต่อด้วย Layout Resource File

ตรง Root element ให้เปลี่ยนเป็น Relativelayout แบบนี้

![Image](https://drive.google.com/uc?id=15JJdcYEqlchlwX6SG-zNZukGwp6aZuID)

### กด OK !!!

# Step 2

หลังสร้างทุกอย่างใน Step 1 เสร็จแล้วมาที่ fragment1_activity.xml เราจะสร้าง Date-picker กัน

มาสร้าง Layout กันก่อน 

```xml
<RelativeLayout
    ......
    android:padding="15dp">

    <TextView
        android:id="@+id/date"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Date : "
        android:textSize="20sp"
        android:layout_marginTop="20dp"/>

    <EditText
        android:id="@+id/date_edit"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_toRightOf="@id/date"
        android:layout_marginTop="10dp"
        android:hint="Enter Date"
        android:ems="20"
        android:textAlignment="center"
        android:cursorVisible="false"/>

</RelativeLayout>
```

ก็จะได้หน้าตาประมาณนี้
![Image](https://drive.google.com/uc?id=1T9q7ZtxM1oEPkQSXRv4x7XsN1B1jYRdb)

# Step 3

เริ่มด้วยสร้าง Fragment เพิ่มขึ้นมาอีกหนึ่งอันตั้งชื่อว่า DatePickerFragment แต่รอบนี้ Subclass ให้เว้นว่างเอาไว้

```java
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerFragment extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener{

    private static final String TAG = "DatePickerFragment";
    final Calendar c = Calendar.getInstance();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), DatePickerFragment.this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        String selectedDate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).format(c.getTime());

        Log.d(TAG, "onDateSet: " + selectedDate);
        getTargetFragment().onActivityResult(
                getTargetRequestCode(),
                Activity.RESULT_OK,
                new Intent().putExtra("selectedDate", selectedDate)
        );
    }
}
```
ให้กลับไปที่ Fragment1.java เราจะมาทำให้ EditText คลิกแล้วมีปฏิทินโผล่ขึ้นมา

```java
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class Fragment1 extends Fragment {
    public static final int REQUEST_CODE = 11;
    EditText editText;
    String selectedDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_activity, container, false);
        editText = view.findViewById(R.id.date_edit);

        final FragmentManager fm = getActivity().getSupportFragmentManager();

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDialogFragment newFragment = new DatePickerFragment();
                newFragment.setTargetFragment(Fragment1.this,REQUEST_CODE);
                newFragment.show(fm, "datePicker");
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check for the results
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // get date from string
            selectedDate = data.getStringExtra("selectedDate");
            // set the value of the editText
            editText.setText(selectedDate);
        }
    }
}
```

เสร็จให้ให้กลับมาที่ MainActivity.java เพื่อเรียกใช้งาน Fragment1 และต้องสร้างที่สำหรับรองรับ Fragment ด้วย

เริ่มจาก MainActivity ก่อน

```java
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        btn1= findViewById(R.id.open1);
    }

    public void openfr1(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment1()).addToBackStack(null).commit();
    }
}
```
ต่อไปก็ มาทำปุ่มใน Layout ของ MainActivity เพื่อเอาไว้สำหรับกดเรียก Fragment1

```xml
<RelativeLayout
    ......
    
    <Button
        android:id="@+id/open1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Open Fragment 1"
        android:onClick="openfr1"
        android:layout_centerHorizontal="true" />

    <FrameLayout
        android:id="@+id/fragment_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@id/open1">

    </FrameLayout>

</RelativeLayout>
```
# Well done 👍 ทีนี้ลองกดเล่นได้เลย ^ ^

## ต่อไปจะเป็น Time - picker อย่าเพิ่งกดปิด Date - picker นะเพราะเราจะทำต่อในนั้น

# Time - Picker

# Step 1

เริ่มจาก Layout ของ MainActivity กันก่อนเลย จะมีการเปลี่ยนแปลงนิดหน่อย

```xml
<RelativeLayout
   ....
   
    <RelativeLayout
        android:id="@+id/btn_group"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true">

        <Button
            android:id="@+id/open1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Open Fragment 1"
            android:onClick="openfr1"/>

        <Button
            android:id="@+id/open2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Open Fragment 2"
            android:onClick="openfr2"
            android:layout_toRightOf="@id/open1"
            android:layout_centerHorizontal="true" />

    </RelativeLayout>

    <FrameLayout
        android:id="@+id/fragment_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@id/btn_group">

    </FrameLayout>

</RelativeLayout>
```
# Step 2

**หลังจากนั้นสร้าง Fragment2 และ Layout ของมันเพิ่ม แล้วมากลับมาที่ Mainactivity**

```java
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        btn1= findViewById(R.id.open1);
    }

    public void openfr1(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment1()).addToBackStack(null).commit();
    }

    public void openfr2(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment2()).addToBackStack(null).commit();
    }
}
```
# Step 3

สร้าง Fragment เพิ่มขึ้นมาอีกหนึ่งอันตั้งชื่อว่า TimePickerFragment โดย Subclass ให้เว้นว่างเอาไว้

```java
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimePickerFragment extends AppCompatDialogFragment implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "TimePickerFragment";
    final Calendar c = Calendar.getInstance();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,minute);

        String selectedTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(c.getTime());
        Log.d(TAG, "onDateSet: " + selectedTime);
        getTargetFragment().onActivityResult(
                getTargetRequestCode(),
                Activity.RESULT_OK,
                new Intent().putExtra("selectedTime", selectedTime)
        );
    }
}
```
# Step 4

ในส่วนของ Fragment2 จะมีความคล้ายกับ Fragment1 อยู่

```java
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class Fragment2 extends Fragment {
    public static final int REQUEST_CODE = 11;
    EditText editText;
    String selectedTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment2_activity,container,false);
        editText = rootview.findViewById(R.id.time_edit);

        final FragmentManager fm = getActivity().getSupportFragmentManager();

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDialogFragment timeFragment = new TimePickerFragment();
                timeFragment.setTargetFragment(Fragment2.this,REQUEST_CODE);
                timeFragment.show(fm,"timePicker");
            }
        });

        return rootview;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check for the results
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // get Time from string
            selectedTime = data.getStringExtra("selectedTime");
            // set the value of the editText
            editText.setText(selectedTime);
        }
    }
}
```
# Well done 👍 ทีนี้ลองกดเล่นได้เลย ^ ^

ก็จบกันไปแล้วกับ Date - picker และ Time - picker

ขอบคุณที่สละเวลาอ่านครับ

**Created by Titiwat Kuarkamphun**




