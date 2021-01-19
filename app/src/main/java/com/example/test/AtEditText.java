package com.example.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AtEditText extends AppCompatEditText {

    private static final String BEFORE = "<!--#";
    private static final String AFTER = "-->";
    private static final String TAG = "AtEditText";

    public static final int CODE_PERSON = 0x05;
    public static final String KEY_CID = "key_id";
    public static final String KEY_NAME = "key_name";
    List<DynamicDrawableSpan> spans = new ArrayList<>();

    /**
     * 存储@的cid、name对,需要使用有序map
     */
    private Map<String, Person> personMap = new LinkedHashMap<>();
    private int num = 0;


    public AtEditText(Context context) {
        this(context, null);
    }

    public AtEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AtEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFilters(new InputFilter[]{new MyInputFilter()});
        requestFocus();

    }

    public int checkAtLength() {
        //at人的字符数量
        int curAtLength = 0;
        for (Person m :
                personMap.values()) {
            curAtLength = curAtLength + m.getName().length();
        }
        Log.i(TAG, "at人的字符长度 = " + curAtLength);
        return curAtLength;
    }

    /**
     * 识别输入框的是不是@符号
     */
    private class MyInputFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            if (source.toString().equalsIgnoreCase("@")
                    || source.toString().equalsIgnoreCase("＠")) {
                if (onJumpListener != null) {
                    onJumpListener.goToChooseContact(CODE_PERSON);
                }
            }
            return source;
        }
    }

    /**
     * 设置span
     *
     * @param keyId   人员的id
     * @param nameStr 人员的名字
     */
    private void setImageSpan(String keyId, String nameStr) {
        int startIndex = getSelectionStart();//光标的位置
        int endIndex = startIndex + nameStr.length();//字符结束的位置

        String tag = BEFORE + num + AFTER;
        num++;

        Person lBean = new Person();
        lBean.setId(keyId);
        lBean.setName("@" + nameStr);
        lBean.setStartIndex(startIndex);
        lBean.setEndIndex(endIndex);
        lBean.setTag(tag);

        //插入要添加的字符，此处是为了给span占位
        getText().insert(startIndex, "@" + nameStr);

        //要先插入，让其他span的位置更新后再获取
        resetSpan(getText());
        //最后把插入的span的信息再放到map中
        personMap.put(lBean.getTag(), lBean);

        //1、使用mEditText构造一个SpannableString
        SpannableString ss = new SpannableString(getText().toString());
        //2、遍历添加span
        for (Person p : personMap.values()) {
            Log.i(TAG, "==========每一个人的位置 start = " + p.getStartIndex() + "  end = " + p.getEndIndex() + "  id = " + p.getId() + "  name = " + p.getName() + "  edittext.tostring = " + getText().toString());
            LDSpan dynamicDrawableSpan = new LDSpan(getContext(), p);
            spans.add(dynamicDrawableSpan);

            // 把取到的要@的人名，用DynamicDrawableSpan代替,使用这个span是为了防止在@人名中间插入任何字符
            //注意start和end的范围是前闭后开即[start,end)所以end要加1
            ss.setSpan(dynamicDrawableSpan, p.getStartIndex(), p.getEndIndex() + 1,
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        setTextKeepState(ss);

    }

    /**
     * 重新计算每一个span的位置重置map
     *
     * @param editable 传入需要识别的editable
     */
    private void resetSpan(Editable editable) {
        if (editable == null) return;
        Log.i(TAG, "===================resetSpan=================");
        LDSpan[] spans = editable.getSpans(0, getText().length(), LDSpan.class);

        personMap.clear();

        for (LDSpan s : spans) {
            Log.i(TAG, "  start = " + getText().getSpanStart(s) + "  end = " + getText().getSpanEnd(s));
            Person p = s.getPerson();
            p.setStartIndex(editable.getSpanStart(s));
            p.setEndIndex(editable.getSpanEnd(s) - 1);
            personMap.put(s.getPerson().getTag(), s.getPerson());
        }

        Log.i(TAG, "spans.length = " + spans.length + "  " + personMap.size());
    }

    private OnJumpListener onJumpListener;

    public interface OnJumpListener {
        void goToChooseContact(int requestCode);
    }

    //对外方法
    public void setOnJumpListener(OnJumpListener onJumpListener) {
        this.onJumpListener = onJumpListener;
    }

    public void handleResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_PERSON && resultCode == Activity.RESULT_OK) {

            String keyId = data.getStringExtra(KEY_CID);

            String nameStr = data.getStringExtra(KEY_NAME);

            data.getBundleExtra("");

            Log.i(TAG, "keyId = " + keyId + "   nameStr = " + nameStr);
            if (TextUtils.isEmpty(keyId) || TextUtils.isEmpty(nameStr)) return;
            //1、判断是否已经添加过，如果添加过则删除@符号并返回
            //找到当前的光标位置,也就是输入@符号的位置
            int curIndex = getSelectionStart();
            if (curIndex >= 1) {
                //删除@符号
                getText().replace(curIndex - 1, curIndex, "");
            }

//            if (personMap.containsKey(keyId)) {
//                //根据id判断，如果已经添加过则不做任何操作，直接返回
//                return;
//            } else {
            //2、没有添加过则构造span，添加到Edittext中
            setImageSpan(keyId, nameStr);
//            }
        }
    }


    //上传需要的内容
    public PublishContent getPublishContent() {
        if (getText() == null) return null;

        //获取全部字符
        StringBuilder text = new StringBuilder(getText().toString());
        //上传的用户信息的数组
        List<Person> personList = new ArrayList<>();
        //获取所有有样式的字符
        LDSpan[] spans = getText().getSpans(0, getText().length(), LDSpan.class);
        //如果没有span，即没有at内容，直接返回转义后的字符串
        if (spans == null || spans.length <= 0) {
            return new PublishContent(htmlEncode(text.toString()), new ArrayList<Person>());
        }
        //保存span样式和下标的hashmap
        HashMap<Integer, LDSpan> spanHashMap = new HashMap<>();
        //span的下标数组
        List<Integer> starts = new ArrayList<>();
        for (LDSpan s : spans) {
            spanHashMap.put(getText().getSpanStart(s), s);
            starts.add(getText().getSpanStart(s));
        }
        //给下标数组排个序
        Collections.sort(starts);

        //将内容根据span的位置划分
        List<PublishPostContent> contents = new ArrayList<>();
        //每一个span的前一个span的end位置，为了获取当前部分的内容，前面获取过的不会再重复获取
        int startIndex = 0;
        for (int i = 0; i < starts.size(); i++) {
            //获取span样式和对应下标
            int start = starts.get(i);
            LDSpan span = spanHashMap.get(start);
            if (span == null) continue;
            //根据span获取用户信息，并保存
            Person p = span.getPerson();
            //用来替换@信息的标签
            String tag = BEFORE + personList.size() + AFTER;
            //保存用户信息（先获取标签再保存，保证标签从0开始）
            personList.add(p);
            //获取上次的end位置到本次span的内容
            String first = text.substring(startIndex, getText().getSpanStart(span));
            //获取本次span之后的所有内容（可能包含下一个span）
            String second = text.substring(getText().getSpanEnd(span));
            //更新span的end位置
            startIndex = getText().getSpanEnd(span);
            //span之前的内容可以保证，只有用户输入内容，不包括@信息
            PublishPostContent content1 = new PublishPostContent(first, -1, -1);
            //插入标签
            PublishPostContent content2 = new PublishPostContent(tag, 1, -1);
            contents.add(content1);
            contents.add(content2);
            if (i == starts.size() - 1) {
                //如果span是最后一个了，也就是second的内容最多只包含用户输入内容，就将最后内容也插入到数组中
                PublishPostContent content3 = new PublishPostContent(second, -1, -1);
                contents.add(content3);
            }
        }
        text = new StringBuilder();
        for (PublishPostContent content : contents) {
            if (content.getType() == 1) {
                //如果是at内容，就不转义
                text.append(content.getContent());
            } else {
                //用户输入内容就转义
                text.append(htmlEncode(content.getContent()));
            }
        }
        PublishContent content = new PublishContent(text.toString(), personList);
        return content;
    }

    /**
     * 字符串转义
     *
     * @param s 输入字符串
     * @return 转义后字符串
     */
    public static String htmlEncode(String s) {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;"); //$NON-NLS-1$
                    break;
                case '>':
                    sb.append("&gt;"); //$NON-NLS-1$
                    break;
                case '&':
                    sb.append("&amp;"); //$NON-NLS-1$
                    break;
                case '\'':
                    sb.append("&apos;"); //$NON-NLS-1$
                    break;
                case '"':
                    sb.append("&quot;"); //$NON-NLS-1$
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }


    public boolean checkPublishContent() {
        if (!TextUtils.isEmpty(getText().toString())) {
            int contentLength = getText().toString().length();
            int atLength = checkAtLength();
            if (contentLength - atLength > 4) {
                Toast.makeText(getContext(), "最多输入200字", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } else {
            Toast.makeText(getContext(), "输入内容为空", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
