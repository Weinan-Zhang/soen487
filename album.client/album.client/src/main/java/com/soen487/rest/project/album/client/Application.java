package com.soen487.rest.project.album.client;

import com.soen487.rest.project.album.client.library.JerseyHttpClientLibrary;
import com.soen487.rest.project.album.client.library.ServletHttpClientLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

@SpringBootApplication
public class Application {
	public static void main(String[] args) throws IOException {

//		SpringApplication.run(Application.class, args);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Select one of below options to send request to consume album services:\n");
		int selectedOption = -1;
		JerseyHttpClientLibrary jserseyHttpClientLibrary = JerseyHttpClientLibrary.getInstance();
		jserseyHttpClientLibrary.initConfigClient(null);
		do{
			System.out.println("input [1] to get all albums:");
			System.out.println("input [2] to get details of an album:");
			System.out.println("input [3] to add a new album:");
			System.out.println("input [4] to modify an album:");
			System.out.println("input [5] to delete an album:");
			System.out.println("input [6] to get all artists:");
			System.out.println("input [7] to get details of an artists:");
			System.out.println("input [8] to add a new artists:");
			System.out.println("input [9] to modify an artists:");
			System.out.println("input [10] to modify an artists:");
			System.out.println("input [0] to quit.");
			selectedOption = scanner.nextInt();
			scanner.nextLine();

			if(selectedOption==1){
				String response = jserseyHttpClientLibrary.httpGetList("http://localhost:8080/album", "list");
				System.out.println("result is:\n" + response);
			}
			else if(selectedOption==2){
				System.out.println("please enter the isrc of the album you want to find:");
				String isrc = scanner.nextLine();
				String response = jserseyHttpClientLibrary.httpGetDetailByIsrc("http://localhost:8080/album", "detail", isrc);
				System.out.println("the result is:\n" + response);
			}
			else if(selectedOption==3){
				HashMap<String, String> formData = new HashMap<>();
				System.out.println("please enter the isrc of the album you want to add:");
				String isrc = scanner.nextLine();
				formData.put("isrc", isrc);
				System.out.println("please enter the title of the album you want to add:");
				String title = scanner.nextLine();
				formData.put("title", title);
				System.out.println("please enter the description of the album you want to add:");
				String description = scanner.nextLine();
				formData.put("description", description);
				System.out.println("please enter the released_year of the album you want to add:");
				String released_year = scanner.nextLine();
				formData.put("released_year", released_year);
				String response = jserseyHttpClientLibrary.httpPostAlbum("http://localhost:8080/album", "add", formData);
				System.out.println("the result is:\n" + response);
			}
			else if(selectedOption==4){
				HashMap<String, String> formData = new HashMap<>();
				System.out.println("please enter the isrc of the album you want to modify:");
				String isrc = scanner.nextLine();
				formData.put("isrc", isrc);
				System.out.println("please enter the title of the album you want to modify:");
				String title = scanner.nextLine();
				formData.put("title", title);
				System.out.println("please enter the description of the album you want to modify:");
				String description = scanner.nextLine();
				formData.put("description", description);
				System.out.println("please enter the released_year of the album you want to modify:");
				String released_year = scanner.nextLine();
				formData.put("released_year", released_year);
				System.out.println("please enter the nickname of the artist of the album you want to modify:");
				String nickname = scanner.nextLine();
				formData.put("nickname", nickname);
				System.out.println("please enter the firstname of the artist of the album you want to modify:");
				String firstname = scanner.nextLine();
				formData.put("firstname", firstname);
				System.out.println("please enter the lastname of the artist of the album you want to modify:");
				String lastname = scanner.nextLine();
				formData.put("lastname", lastname);
				System.out.println("please enter the biography of the artist of the album you want to modify:");
				String biography = scanner.nextLine();
				formData.put("biography", biography);
				String response = jserseyHttpClientLibrary.httpPutAlbum("http://localhost:8080/album", "update", formData);
				System.out.println("the result is:\n" + response);
			}
			else if(selectedOption==5){
				System.out.println("please enter the isrc of the album you want to delete:");
				String isrc = scanner.nextLine();
				String response = jserseyHttpClientLibrary.httpDeleteAlbum("http://localhost:8080/album", "delete", isrc);
				System.out.println("the result is:\n" + response);
			}
			else if(selectedOption==6){
				String response = ServletHttpClientLibrary.executeGetRequest("http://localhost:8100/artist", "target=artist&operation=list");
				System.out.println("the result is:\n" + response);
			}
			else if(selectedOption==7){
				System.out.println("please enter the nick name of the artist you want to get:");
				String nickname = scanner.nextLine();
				nickname = java.net.URLEncoder.encode(nickname, "UTF-8").replace("+", "%20");
				String response = ServletHttpClientLibrary.executeGetRequest("http://localhost:8100/artist", "target=artist&operation=detail&nickname="+nickname);
				System.out.println("the result is:\n" + response);
			}
			else if(selectedOption==8){
				HashMap<String, String> formData = new HashMap<String, String>();
				formData.put("target","artist");
				formData.put("operation","add");
				System.out.println("please enter the nickname of the artist of the album you want to add:");
				String nickname = scanner.nextLine();
				formData.put("nickname",nickname);
				System.out.println("please enter the firstname of the artist of the album you want to add:");
				String firstname = scanner.nextLine();
				formData.put("firstname",firstname);
				System.out.println("please enter the lastname of the artist of the album you want to add:");
				String lastname = scanner.nextLine();
				formData.put("lastname",lastname);
				System.out.println("please enter the biography of the artist of the album you want to add:");
				String biography = scanner.nextLine();
				formData.put("biography",biography);
				String response = ServletHttpClientLibrary.executePostRequest("http://localhost:8100/artist", formData);
				System.out.println("the result is:\n" + response);

			}
			else if(selectedOption==9){
				HashMap<String, String> formData = new HashMap<String, String>();
				formData.put("target","artist");
				formData.put("operation","update");
				System.out.println("please enter the nickname of the artist of the album you want to modify:");
				String nickname = scanner.nextLine();
				formData.put("nickname",nickname);
				System.out.println("please enter the firstname of the artist of the album you want to modify:");
				String firstname = scanner.nextLine();
				formData.put("firstname",firstname);
				System.out.println("please enter the lastname of the artist of the album you want to modify:");
				String lastname = scanner.nextLine();
				formData.put("lastname",lastname);
				System.out.println("please enter the biography of the artist of the album you want to modify:");
				String biography = scanner.nextLine();
				formData.put("biography",biography);
				String response = ServletHttpClientLibrary.executePutRequest("http://localhost:8100/artist", formData);
				System.out.println("the result is:\n" + response);

			}
			else if(selectedOption==10){
				System.out.println("please enter the nick name of the artist you want to delete:");
				String nickname = scanner.nextLine();
				nickname = java.net.URLEncoder.encode(nickname, "UTF-8").replace("+", "%20");
				String response = ServletHttpClientLibrary.executeDeleteRequest("http://localhost:8100/artist", "target=artist&operation=delete&nickname="+nickname);
				System.out.println("the result is:\n" + response);
			}
		}
		while(selectedOption!=0);
	}

}
