package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {

    private HashMap<String, Student> studentMap;
    private HashMap<String, Teacher> teacherMap;
    private HashMap<String, List<String>> teacherStudentMapping;

    public StudentRepository(){
        this.studentMap = new HashMap<>();
        this.teacherMap = new HashMap<>();
        this.teacherStudentMapping = new HashMap<>();
    }

    public void saveStudent(Student student){
        String name = student.getName();
        studentMap.put(name, student);
    }

    public void saveTeacher(Teacher teacher){
        String name = teacher.getName();
        teacherMap.put(name, teacher);
    }

    public void saveStudentTeacherPair(String student, String teacher){
        if(studentMap.containsKey(student) && teacherMap.containsKey(teacher)){
            teacherStudentMapping.putIfAbsent(teacher, new ArrayList<>());
            teacherStudentMapping.get(teacher).add(student);
        }
    }

    public Student findStudent(String student){

        if(studentMap.containsKey(student)){
            return studentMap.get(student);
        }
        return null;
    }

    public Teacher findTeacher(String teacher){

        if(teacherMap.containsKey(teacher)){
            return teacherMap.get(teacher);
        }
        return null;
    }

    public List<String> findStudentsFromTeacher(String teacher){
        return teacherStudentMapping.getOrDefault(teacher, new ArrayList<>());
    }

    public List<String> findAllStudents(){
        return new ArrayList<>(studentMap.keySet());
    }

    public void deleteTeacher(String teacher){
        if(teacherMap.containsKey(teacher)){
            teacherMap.remove(teacher);
            if(teacherStudentMapping.containsKey(teacher)){
                List<String> students = teacherStudentMapping.get(teacher);
                for(String student : students){
                    studentMap.remove(student);
                }
                teacherStudentMapping.remove(teacher);
            }
        }
    }

    public void deleteAllTeachers(){
        teacherMap.clear();
        teacherStudentMapping.clear();
        studentMap.clear();
    }
}
