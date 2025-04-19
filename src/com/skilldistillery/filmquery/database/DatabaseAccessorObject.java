package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid";
	private static final String user = "student";
	private static final String pass = "student";

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;

		try {
			// connect to database
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT language.name, film.* FROM film JOIN language "
					+ "ON language_id = language.id WHERE film.id = ? ";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, filmId);
			// execute query
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				film = new Film();
				film.setId(result.getInt("id"));
				film.setTitle(result.getString("title"));
				film.setDescription(result.getString("description"));
				film.setReleaseYear(result.getInt("release_year"));
				film.setLanguageId(result.getInt("language_id"));
				film.setLanguage(result.getString("name"));
				film.setRentalDuration(result.getInt("rental_duration"));
				film.setRentalRate(result.getInt("rental_rate"));
				film.setLength(result.getInt("length"));
				film.setReplacementCost(result.getDouble("replacement_cost"));
				film.setRating(result.getString("rating"));
				film.setSpecialFeatures(result.getString("special_features"));

			}
			result.close();
			conn.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;

	}

	@Override
	public Film findFilmByKeyword(String keyword) {
		Film film = null;

		try {
			// connect to database
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT language.name, film.* FROM film JOIN language "
					+ "ON language_id = language.id WHERE film.title LIKE ? OR film.description LIKE ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1,"%" + keyword + "%");
			statement.setString(2,"%" + keyword + "%");
			// execute querySELECT * FROM film WHERE title = %?%
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				film = new Film();
				film.setId(result.getInt("id"));
				film.setTitle(result.getString("title"));
				film.setDescription(result.getString("description"));
				film.setReleaseYear(result.getInt("release_year"));
				film.setLanguageId(result.getInt("language_id"));
				film.setLanguage(result.getString("name"));
				film.setRentalDuration(result.getInt("rental_duration"));
				film.setRentalRate(result.getInt("rental_rate"));
				film.setLength(result.getInt("length"));
				film.setReplacementCost(result.getDouble("replacement_cost"));
				film.setRating(result.getString("rating"));
				film.setSpecialFeatures(result.getString("special_features"));

			}
			result.close();
			conn.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT * FROM actor WHERE id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, actorId);

			ResultSet result = statement.executeQuery();
			if (result.next()) {
				actor = new Actor();
				actor.setId(result.getInt("id"));
				actor.setFirstName(result.getString("first_name"));
				actor.setLastName(result.getString("last_name"));
			}
			result.close();
			statement.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actorsList = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT id, first_name, last_name FROM film JOIN film_actor "
					+ "ON film.id = film_actor.actor_id WHERE id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, filmId);

			ResultSet result = statement.executeQuery();

			while (result.next()) {
				int actorId = result.getInt(1);
				String firstName = result.getString(2);
				String lastName = result.getString(3);

				Actor actorListItem = new Actor(actorId, firstName, lastName);
				actorsList.add(actorListItem);
			}
			result.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actorsList;
	}

}
