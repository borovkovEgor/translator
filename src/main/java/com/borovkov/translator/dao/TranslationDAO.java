package com.borovkov.translator.dao;

import com.borovkov.translator.model.Translation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class TranslationDAO {

    private final DataSource dataSource;

    public void save(Translation translation) {
        String sql = "INSERT INTO translation_request (ip_address, source_text, translated_text) VALUES (?, ?, ?)";

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, translation.getIpAddress());
            preparedStatement.setString(2, translation.getSourceText());
            preparedStatement.setString(3, translation.getTranslatedText());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
