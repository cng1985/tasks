package com.halfhour.tasks.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by 年高 on 2015/5/16.
 */

@Table(name = "Task")
public class Task extends Model {

    @Column(name = "Name")
    public String name;


    @Column(name = "contents")
    public String contents;

    @Column(name = "addDate")
    public Date addDate;
}
