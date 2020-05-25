package com.example.demo.repository.JDBCRepository;

import com.example.demo.model.Student;
import com.example.demo.model.Subject;
import com.example.demo.model.TestScore;
import com.example.demo.repository.TestScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class JDBCTestScoreRepository implements TestScoreRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(TestScore testScore) {
        String sql= "INSERT INTO testscore(score1, score2, finalScore, summaryScore, id_subject,id_student)" +
                " VALUES(?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,testScore.getFirstScore(), testScore.getSecondScore(),
                testScore.getFinalScore(), testScore.getSummaryScore(), testScore.getId_subject(), testScore.getId_student());
    }

    @Override
    public int update(TestScore testScore) {
        String sql= "UPDATE testScore SET score1 = ?, score2=?, finalScore=?, id_subject=?, id_student=? WHERE id=?";
        return jdbcTemplate.update(sql, testScore.getFirstScore(),testScore.getSecondScore(),testScore.getFinalScore(),
                testScore.getId_subject(), testScore.getId_subject(),testScore.getId());
    }

    @Override
    public int deleteById(Long id) {
        String sql= "UPDATE testscore SET isDeleted=1 WHERE id=?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public List<TestScore> findAll() {
        String sql= "SELECT * FROM testscore WHERE isDeleted=0";
        List<TestScore> testScores = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper(TestScore.class));
        return testScores;
    }

    @Override
    public TestScore findById(Long id) {
        String sql = "SELECT * FROM testscore WHERE id = ? AND isDeleted=0";
        return (TestScore) jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                new BeanPropertyRowMapper(TestScore.class));
    }

    @Override
    public List<TestScore> findAllByIdStudent(Long id) {
        String sql= "SELECT s.name as name_student,  sub.name as name_subject, sco.score1, sco.score2, sco.finalscore, sco.summaryscore FROM testscore sco " +
                "INNER JOIN students s ON sco.id_student = s.id INNER JOIN subjects sub ON sco.id_subject=sub.id WHERE s.id =" + id + " AND s.isDeleted = 0";

        List<TestScore> testScores = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            TestScore obj = new TestScore();
            obj.setName_student((String) row.get("name_student"));
            obj.setName_subject((String) row.get("name_subject"));
            obj.setFirstScore((double) row.get("score1"));
            obj.setSecondScore((double) row.get("score2"));
            obj.setFinalScore((double) row.get("finalscore"));
            obj.setSummaryScore((double) row.get("summaryscore"));
            testScores.add(obj);
        }
        return testScores;
    }

    @Override
    public List<TestScore> findAllByIdClassAndIdSubject(Long id_class, Long id_subject) {
        String sql = "SELECT s.id as id_student, s.name as name_student, sco.score1, sco.score2, sco.finalscore, sco.summaryscore FROM students s " +
                "INNER JOIN testscore sco ON s.id= sco.id_student WHERE s.id_class ="+ id_class+" AND sco.id_subject ="+ id_subject+" AND s.isDeleted=0";
        List<TestScore> testScores = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            TestScore obj = new TestScore();
            obj.setId((Long) row.get("id_student")) ;
            obj.setName_student((String) row.get("name_student"));
            obj.setFirstScore((double) row.get("score1"));
            obj.setSecondScore((double) row.get("score2"));
            obj.setFinalScore((double) row.get("finalscore"));
            obj.setSummaryScore((double) row.get("summaryscore"));
            testScores.add(obj);
        }
        return testScores;
    }
}
