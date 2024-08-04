package com.borovkov.translator.dao;

import com.borovkov.translator.model.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LanguageDAO {

    private final DataSource dataSource;

    public List<Language> getAllLanguages() {
        List<Language> languages = new ArrayList<>();

        try(Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM languages ORDER BY name ASC";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Language language = new Language();
                language.setId(resultSet.getLong("id"));
                language.setCode(resultSet.getString("code"));
                language.setName(resultSet.getString("name"));

                languages.add(language);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return languages;
    }
}
