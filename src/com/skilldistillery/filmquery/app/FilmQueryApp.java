package com.skilldistillery.filmquery.app;

import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	private Scanner input = new Scanner(System.in);
	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

	private void test() {
		Film film = db.findFilmById(1);
		System.out.println(film);
	}

	private void launch() {
		boolean isTrue = true;
		do {
			if (startUserInterface() == false) {
				isTrue = false;
			}
		} while (isTrue);

		input.close();
	}

	private boolean startUserInterface() {
		showMenu();
		String menuChoice = input.nextLine();

		switch (menuChoice) {
		case "1":
			System.out.println("Please enter the id of the film you wish to find: ");
			int userIdChoice = input.nextInt();

			Film filmId = db.findFilmById(userIdChoice);

			if (filmId == null) {
				System.out.println("Sorry! The film id you put in doesn't exist!");
				break;
			} else {
				System.out.println(filmId.getTitle() +  "| Language: " + filmId.getLanguage() + " | Released in " + filmId.getReleaseYear() + " | Film rating ("
						+ filmId.getRating() + ") | Description: " + filmId.getDescription());
				input.nextLine();
			}
			break;
		case "2":
			System.out.println("Please enter the keyword for your search: ");
			String keyword = input.nextLine();
			
			Film filmKey = db.findFilmByKeyword(keyword);
			
			if (filmKey == null) {
				System.out.println("Sorry! The film keyword you put in doesn't exist!");
				break;
			} else {
				System.out.println(filmKey.getTitle() + " | Released in " + filmKey.getReleaseYear() + " | Film rating ("
						+ filmKey.getRating() + ") | Description: " + filmKey.getDescription());
				input.nextLine();
				break;
			}
			
		case "3":
			System.out.println("We thanks you for stopping by at BlockBlister!!");
			return false;
		default:
			System.out.println("Oooooooh sorry that input has no value to me, try again!");
		}
		return false;
	}

	private void showMenu() {
		System.out.println("********************************************************************");
		System.out.println("WELCOME TO BLOCKBLISTER, WHERE THE MOVIES ARE BETTER!!");
		System.out.println("MUUUUCH BETTER!!!");
		System.out.println();
		System.out.println("How might we be helping you? ");
		System.out.println("1. Look up a film by id");
		System.out.println("2. Look up a film by the search keyword");
		System.out.println("3. Exit the application");
		System.out.println("Please enter the number that matches the menu item you wish to select");

	}

}
