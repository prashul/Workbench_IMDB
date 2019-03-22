import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class populate {

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String CONNECTION = "jdbc:oracle:thin:@localhost:1521:oracle";
	private static final String USER = "scott";
	private static final String PASSWORD = "tiger";
	public static Connection dbConnection = null;

	public static void main(String[] Filenames) {

		String BasePath = "D:/Academics/Quarter 2/Database System/Assignment 3/hetrec2011-movielens-2k-v2/";

		String[] Tables = { "movies", "movie_genres", "movie_directors", "movie_actors", "movie_countries",
				"movie_locations", "tags", "movie_tags", "user_taggedmovies_timestamps", "user_taggedmovies",
				"user_ratedmovies_timestamps", "user_ratedmovies" };

		dbConnection = getDBConnection();

		deleteData(Tables);

		populateMovies(BasePath + Filenames[0]);
		populateMovieGenres(BasePath + Filenames[1]);
//		populateMovieDirectors(BasePath + Filenames[2]);
//		populateMovieActors(BasePath + Filenames[3]);
//		populateMovieCountries(BasePath + Filenames[4]);
//		populateMovieLocations(BasePath + Filenames[5]);
//		populateTags(BasePath + Filenames[6]);
//		populateMovieTags(BasePath + Filenames[7]);
//		populateUserTtaggedMoviesTimestamps(BasePath + Filenames[8]);
//		populateUserTaggedMovies(BasePath + Filenames[9]);
//		populateUserRatedMoviesTimestamps(BasePath + Filenames[10]);
//		populateUserRatedMovies(BasePath + Filenames[11]);
	}

	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

	private static void deleteData(String[] Tables) {
		PreparedStatement ps = null;
		for (int i = Tables.length - 1; i >= 0; i--) {
			String sqlStatement = "DELETE FROM " + Tables[i];
			try {
				ps = dbConnection.prepareStatement(sqlStatement);
				ps.executeUpdate();
				System.out.println("Data Deleted from : " + Tables[i]);
			} catch (SQLException e) {
				System.out.println("Prepared Statement Error : Delete Data");
				e.printStackTrace();
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println("Prepared Statement Close Error : Delete Data");
					e.printStackTrace();
				}
			}

		}

	}

	private static void populateMovies(String FileName) {
		BufferedReader br = null;
		String line = "";
		String sqlStatement = "INSERT INTO movies VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = null;
		String id;
		String title;
		String imdbID;
		String spanishTitle;
		String imdbPictureURL;
		int year;
		String rtID;
		float rtAllCriticsRating;
		int rtAllCriticsNumReviews;
		int rtAllCriticsNumFresh;
		int rtAllCriticsNumRotten;
		int rtAllCriticsScore;
		float rtTopCriticsRating;
		int rtTopCriticsNumReviews;
		int rtTopCriticsNumFresh;
		int rtTopCriticsNumRotten;
		int rtTopCriticsScore;
		float rtAudienceRating;
		int rtAudienceNumRatings;
		int rtAudienceScore;
		String rtPictureURL;

		try {
			br = new BufferedReader(new FileReader(FileName));
			line = br.readLine();
			while ((line = br.readLine()) != null) {

				String data[] = line.split("\t");

				id = (data.length > 0) ? data[0] : "";
				title = (data.length > 1) ? data[1] : "";
				imdbID = (data.length > 2) ? data[2] : "";
				spanishTitle = (data.length > 3) ? data[3] : "";
				imdbPictureURL = (data.length > 4) ? data[4] : "";
				year = (data.length > 5) ? Integer.parseInt(data[5]) : 0;
				rtID = (data.length > 6) ? data[6] : "";
				rtAllCriticsRating = ((data.length > 7) && (!data[7].equals("\\N"))) ? Float.valueOf(data[7]) : 0;
				rtAllCriticsNumReviews = ((data.length > 8) && (!data[8].equals("\\N"))) ? Integer.valueOf(data[8]) : 0;
				rtAllCriticsNumFresh = ((data.length > 9) && (!data[9].equals("\\N"))) ? Integer.valueOf(data[9]) : 0;
				rtAllCriticsNumRotten = ((data.length > 10) && (!data[10].equals("\\N"))) ? Integer.valueOf(data[10])
						: 0;
				rtAllCriticsScore = ((data.length > 11) && (!data[11].equals("\\N"))) ? Integer.valueOf(data[11]) : 0;
				rtTopCriticsRating = ((data.length > 12) && (!data[12].equals("\\N"))) ? Float.valueOf(data[12]) : 0;
				rtTopCriticsNumReviews = ((data.length > 13) && (!data[13].equals("\\N"))) ? Integer.valueOf(data[13])
						: 0;
				rtTopCriticsNumFresh = ((data.length > 14) && (!data[14].equals("\\N"))) ? Integer.valueOf(data[14])
						: 0;
				rtTopCriticsNumRotten = ((data.length > 15) && (!data[15].equals("\\N"))) ? Integer.valueOf(data[15])
						: 0;
				rtTopCriticsScore = ((data.length > 16) && (!data[16].equals("\\N"))) ? Integer.valueOf(data[16]) : 0;
				rtAudienceRating = ((data.length > 17) && (!data[17].equals("\\N"))) ? Float.valueOf(data[17]) : 0;
				rtAudienceNumRatings = ((data.length > 18) && (!data[18].equals("\\N"))) ? Integer.valueOf(data[18])
						: 0;
				rtAudienceScore = ((data.length > 19) && (!data[19].equals("\\N"))) ? Integer.valueOf(data[19]) : 0;
				rtPictureURL = (data.length > 20) ? data[20] : "";

				try {
					ps = dbConnection.prepareStatement(sqlStatement);
					ps.setString(1, id);
					ps.setString(2, title);
					ps.setString(3, imdbID);
					ps.setString(4, spanishTitle);
					ps.setString(5, imdbPictureURL);
					ps.setInt(6, year);
					ps.setString(7, rtID);
					ps.setFloat(8, rtAllCriticsRating);
					ps.setInt(9, rtAllCriticsNumReviews);
					ps.setInt(10, rtAllCriticsNumFresh);
					ps.setInt(11, rtAllCriticsNumRotten);
					ps.setInt(12, rtAllCriticsScore);
					ps.setFloat(13, rtTopCriticsRating);
					ps.setInt(14, rtTopCriticsNumReviews);
					ps.setInt(15, rtTopCriticsNumFresh);
					ps.setInt(16, rtTopCriticsNumRotten);
					ps.setInt(17, rtTopCriticsScore);
					ps.setFloat(18, rtAudienceRating);
					ps.setInt(19, rtAudienceNumRatings);
					ps.setInt(20, rtAudienceScore);
					ps.setString(21, rtPictureURL);

					ps.executeUpdate();

				} catch (SQLException e) {
					System.out.println("Prepared Statement Error : Table -  Movies");
					e.printStackTrace();
				} finally {
					try {
						ps.close();
					} catch (SQLException e) {
						System.out.println("Prepared Statement Close Error : Table -  Movies");
						e.printStackTrace();
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Data Populated to : Table -  Movies");
	}

	private static void populateMovieGenres(String FileName) {
		BufferedReader br = null;
		String line = "";
		String sqlStatement = "INSERT INTO movie_genres VALUES(?, ?)";
		PreparedStatement ps = null;
		String movieID;
		String genre;
		try {
			br = new BufferedReader(new FileReader(FileName));
			line = br.readLine();
			while ((line = br.readLine()) != null) {

				String data[] = line.split("\t");

				movieID = (data.length > 0) ? data[0] : "";
				genre = (data.length > 1) ? data[1] : "";

				try {
					ps = dbConnection.prepareStatement(sqlStatement);
					ps.setString(1, movieID);
					ps.setString(2, genre);
					ps.executeUpdate();
				} catch (SQLException e) {
					System.out.println("Prepared Statement Error : Table -  Movies_Genre");
					e.printStackTrace();
				} finally {
					try {
						ps.close();
					} catch (SQLException e) {
						System.out.println("Prepared Statement Close Error : Table -  Movies_Genre");
						e.printStackTrace();
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Data Populated to : Table -  Movies_Genre");
	}

	private static void populateMovieDirectors(String FileName) {
		BufferedReader br = null;
		String line = "";
		String sqlStatement = "INSERT INTO movie_directors VALUES(?, ?, ?)";
		PreparedStatement ps = null;
		String movieID;
		String directorID;
		String directorName;
		try {
			br = new BufferedReader(new FileReader(FileName));
			line = br.readLine();
			while ((line = br.readLine()) != null) {

				String data[] = line.split("\t");

				movieID = (data.length > 0) ? data[0] : "";
				directorID = (data.length > 1) ? data[1] : "";
				directorName = (data.length > 2) ? data[2] : "";

				try {
					ps = dbConnection.prepareStatement(sqlStatement);
					ps.setString(1, movieID);
					ps.setString(2, directorID);
					ps.setString(3, directorName);
					ps.executeUpdate();
				} catch (SQLException e) {
					System.out.println("Prepared Statement Error : Table - Movie Directors");
					e.printStackTrace();
				} finally {
					try {
						ps.close();
					} catch (SQLException e) {
						System.out.println("Prepared Statement Close Error : Table - Movie Directors");
						e.printStackTrace();
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Data Populated to : Table -  Movie Directors");
	}

	private static void populateMovieActors(String FileName) {
		BufferedReader br = null;
		String line = "";
		String sqlStatement = "INSERT INTO movie_actors VALUES(?, ?, ?, ?)";
		PreparedStatement ps = null;
		String movieID;
		String actorID;
		String actorName;
		int ranking;
		try {
			br = new BufferedReader(new FileReader(FileName));
			line = br.readLine();
			while ((line = br.readLine()) != null) {

				String data[] = line.split("\t");

				movieID = (data.length > 0) ? data[0] : "";
				actorID = (data.length > 1) ? data[1] : "";
				actorName = (data.length > 2) ? data[2] : "";
				ranking = (data.length > 3) ? Integer.valueOf(data[3]) : 0;

				try {
					ps = dbConnection.prepareStatement(sqlStatement);
					ps.setString(1, movieID);
					ps.setString(2, actorID);
					ps.setString(3, actorName);
					ps.setInt(4, ranking);
					ps.executeUpdate();
					
				} catch (SQLException e) {
					System.out.println("Prepared Statement Error : Table - Movie_Actors ");
					e.printStackTrace();
				} finally {
					try {
						ps.close();
					} catch (SQLException e) {
						System.out.println("Prepared Statement Close Error : Table - Movie_Actors ");
						e.printStackTrace();
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Data Populated to : Table - Movie_Actors ");
	}

	private static void populateMovieCountries(String FileName) {
		BufferedReader br = null;
		String line = "";
		String sqlStatement = "INSERT INTO movie_countries VALUES(?, ?)";
		PreparedStatement ps = null;
		String movieID;
		String country;
		try {
			br = new BufferedReader(new FileReader(FileName));
			line = br.readLine();
			while ((line = br.readLine()) != null) {

				String data[] = line.split("\t");

				movieID = (data.length > 0) ? data[0] : "";
				country = (data.length > 1) ? data[1] : "";

				try {
					ps = dbConnection.prepareStatement(sqlStatement);
					ps.setString(1, movieID);
					ps.setString(2, country);
					ps.executeUpdate();

				} catch (SQLException e) {
					System.out.println("Prepared Statement Error : Table - Movie_Countries");
					e.printStackTrace();
				} finally {
					try {
						ps.close();
					} catch (SQLException e) {
						System.out.println("Prepared Statement Close Error : Table - Movie_Countries");
						e.printStackTrace();
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Data Populated to : Table - Movie_Countries ");
	}

	private static void populateMovieLocations(String FileName) {
		BufferedReader br = null;
		String line = "";
		String sqlStatement = "INSERT INTO movie_locations VALUES(?, ?, ?, ?, ?)";
		PreparedStatement ps = null;
		String movieID;
		String location1;
		String location2;
		String location3;
		String location4;
		try {
			br = new BufferedReader(new FileReader(FileName));
			line = br.readLine();
			while ((line = br.readLine()) != null) {

				String data[] = line.split("\t");

				movieID = (data.length > 0) ? data[0] : "";
				location1 = (data.length > 1) ? data[1] : "";
				location2 = (data.length > 2) ? data[2] : "";
				location3 = (data.length > 3) ? data[3] : "";
				location4 = (data.length > 4) ? data[4] : "";

				try {
					ps = dbConnection.prepareStatement(sqlStatement);
					ps.setString(1, movieID);
					ps.setString(2, location1);
					ps.setString(3, location2);
					ps.setString(4, location3);
					ps.setString(5, location4);
					ps.executeUpdate();

				} catch (SQLException e) {
					System.out.println("Prepared Statement Error : Table - Movie_Locations ");
					e.printStackTrace();
				} finally {
					try {
						ps.close();
					} catch (SQLException e) {
						System.out.println("Prepared Statement Close Error : Table - Movie_Locations ");
						e.printStackTrace();
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Data Populated to : Table - Movie_Locations ");
	}

	private static void populateTags(String FileName) {
		BufferedReader br = null;
		String line = "";
		String sqlStatement = "INSERT INTO tags VALUES(?, ?)";
		PreparedStatement ps = null;
		String id;
		String value;
		try {
			br = new BufferedReader(new FileReader(FileName));
			line = br.readLine();
			while ((line = br.readLine()) != null) {

				String data[] = line.split("\t");

				id = (data.length > 0) ? data[0] : "";
				value = (data.length > 1) ? data[1] : "";

				try {
					ps = dbConnection.prepareStatement(sqlStatement);
					ps.setString(1, id);
					ps.setString(2, value);
					ps.executeUpdate();

				} catch (SQLException e) {
					System.out.println("Prepared Statement Error : Table - Tags ");
					e.printStackTrace();
				} finally {
					try {
						ps.close();
					} catch (SQLException e) {
						System.out.println("Prepared Statement Close Error : Table - Tags ");
						e.printStackTrace();
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Data Populated to : Table - Tags ");
	}

	private static void populateMovieTags(String FileName) {
		BufferedReader br = null;
		String line = "";
		String sqlStatement = "INSERT INTO movie_tags VALUES(?, ?, ?)";
		PreparedStatement ps = null;
		String movieID;
		String tagID;
		int tagWeight;
		try {
			br = new BufferedReader(new FileReader(FileName));
			line = br.readLine();
			while ((line = br.readLine()) != null) {

				String data[] = line.split("\t");

				movieID = (data.length > 0) ? data[0] : "";
				tagID = (data.length > 1) ? data[1] : "";
				tagWeight = (data.length > 1) ? Integer.valueOf(data[2]) : 0;

				try {
					ps = dbConnection.prepareStatement(sqlStatement);
					ps.setString(1, movieID);
					ps.setString(2, tagID);
					ps.setInt(3, tagWeight);
					ps.executeUpdate();

				} catch (SQLException e) {
					System.out.println("Prepared Statement Error : Table - Movie_Tags ");
					e.printStackTrace();
				} finally {
					try {
						ps.close();
					} catch (SQLException e) {
						System.out.println("Prepared Statement Close Error : Table - Movie_Tags ");
						e.printStackTrace();
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Data Populated to : Table - Movie_Tags ");
	}

	private static void populateUserTtaggedMoviesTimestamps(String FileName) {
		BufferedReader br = null;
		String line = "";
		String sqlStatement = "INSERT INTO user_taggedmovies_timestamps VALUES(?, ?, ?, ?)";
		PreparedStatement ps = null;
		String userID;
		String movieID;
		String tagID;
		String timestamp;
		try {
			br = new BufferedReader(new FileReader(FileName));
			line = br.readLine();
			while ((line = br.readLine()) != null) {

				String data[] = line.split("\t");

				userID = (data.length > 0) ? data[0] : "";
				movieID = (data.length > 1) ? data[1] : "";
				tagID = (data.length > 2) ? data[2] : "";
				timestamp = (data.length > 3) ? data[3] : "";

				try {
					ps = dbConnection.prepareStatement(sqlStatement);
					ps.setString(1, userID);
					ps.setString(2, movieID);
					ps.setString(3, tagID);
					ps.setString(4, timestamp);
					ps.executeUpdate();

				} catch (SQLException e) {
					System.out.println("Prepared Statement Error : Table - user_taggedmovies_timestamps ");
					e.printStackTrace();
				} finally {
					try {
						ps.close();
					} catch (SQLException e) {
						System.out.println("Prepared Statement Close Error : Table - user_taggedmovies_timestamps ");
						e.printStackTrace();
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Data Populated to : Table - user_taggedmovies_timestamps ");
	}

	private static void populateUserTaggedMovies(String FileName) {
		BufferedReader br = null;
		String line = "";
		String sqlStatement = "INSERT INTO user_taggedmovies VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = null;
		String userID;
		String movieID;
		String tagID;
		int date_day;
		int date_month;
		int date_year;
		int date_hour;
		int date_minute;
		int date_second;

		try {
			br = new BufferedReader(new FileReader(FileName));
			line = br.readLine();
			while ((line = br.readLine()) != null) {

				String data[] = line.split("\t");

				userID = (data.length > 0) ? data[0] : "";
				movieID = (data.length > 1) ? data[1] : "";
				tagID = (data.length > 2) ? data[2] : "";
				date_day = (data.length > 3) ? Integer.valueOf(data[3]) : 0;
				date_month = (data.length > 4) ? Integer.valueOf(data[4]) : 0;
				date_year = (data.length > 5) ? Integer.valueOf(data[5]) : 0;
				date_hour = (data.length > 6) ? Integer.valueOf(data[6]) : 0;
				date_minute = (data.length > 7) ? Integer.valueOf(data[7]) : 0;
				date_second = (data.length > 8) ? Integer.valueOf(data[8]) : 0;

				try {
					ps = dbConnection.prepareStatement(sqlStatement);
					ps.setString(1, userID);
					ps.setString(2, movieID);
					ps.setString(3, tagID);
					ps.setInt(4, date_day);
					ps.setInt(5, date_month);
					ps.setInt(6, date_year);
					ps.setInt(7, date_hour);
					ps.setInt(8, date_minute);
					ps.setInt(9, date_second);
					ps.executeUpdate();

				} catch (SQLException e) {
					System.out.println("Prepared Statement Error : Table - User_Tagged_Movies ");
					e.printStackTrace();
				} finally {
					try {
						ps.close();
					} catch (SQLException e) {
						System.out.println("Prepared Statement Close Error : Table - User_Tagged_Movies ");
						e.printStackTrace();
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Data Populated to : Table - User_Tagged_Movies ");
	}

	private static void populateUserRatedMoviesTimestamps(String FileName) {
		BufferedReader br = null;
		String line = "";
		String sqlStatement = "INSERT INTO user_ratedmovies_timestamps VALUES(?, ?, ?, ?)";
		PreparedStatement ps = null;
		String userID;
		String movieID;
		float rating;
		String timestamp;
		try {
			br = new BufferedReader(new FileReader(FileName));
			line = br.readLine();
			while ((line = br.readLine()) != null) {

				String data[] = line.split("\t");

				userID = (data.length > 0) ? data[0] : "";
				movieID = (data.length > 1) ? data[1] : "";
				rating = (data.length > 2) ? Float.valueOf(data[2]) : 0;
				timestamp = (data.length > 3) ? data[3] : "";

				try {
					ps = dbConnection.prepareStatement(sqlStatement);
					ps.setString(1, userID);
					ps.setString(2, movieID);
					ps.setFloat(3, rating);
					ps.setString(4, timestamp);
					ps.executeUpdate();

				} catch (SQLException e) {
					System.out.println("Prepared Statement Error : Table - User_Ratedmovies_Timestamps ");
					e.printStackTrace();
				} finally {
					try {
						ps.close();
					} catch (SQLException e) {
						System.out.println("Prepared Statement Close Error : Table - User_Ratedmovies_Timestamps ");
						e.printStackTrace();
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Data Populated to : Table - User_Ratedmovies_Timestamps ");
	}

	private static void populateUserRatedMovies(String FileName) {
		BufferedReader br = null;
		String line = "";
		String sqlStatement = "INSERT INTO user_ratedmovies VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = null;
		String userID;
		String movieID;
		float rating;
		int date_day;
		int date_month;
		int date_year;
		int date_hour;
		int date_minute;
		int date_second;

		try {
			br = new BufferedReader(new FileReader(FileName));
			line = br.readLine();
			while ((line = br.readLine()) != null) {

				String data[] = line.split("\t");

				userID = (data.length > 0) ? data[0] : "";
				movieID = (data.length > 1) ? data[1] : "";
				rating = (data.length > 2) ? Float.valueOf(data[2]) : 0;
				date_day = (data.length > 3) ? Integer.valueOf(data[3]) : 0;
				date_month = (data.length > 4) ? Integer.valueOf(data[4]) : 0;
				date_year = (data.length > 5) ? Integer.valueOf(data[5]) : 0;
				date_hour = (data.length > 6) ? Integer.valueOf(data[6]) : 0;
				date_minute = (data.length > 7) ? Integer.valueOf(data[7]) : 0;
				date_second = (data.length > 8) ? Integer.valueOf(data[8]) : 0;

				try {
					ps = dbConnection.prepareStatement(sqlStatement);
					ps.setString(1, userID);
					ps.setString(2, movieID);
					ps.setFloat(3, rating);
					ps.setInt(4, date_day);
					ps.setInt(5, date_month);
					ps.setInt(6, date_year);
					ps.setInt(7, date_hour);
					ps.setInt(8, date_minute);
					ps.setInt(9, date_second);
					ps.executeUpdate();

				} catch (SQLException e) {
					System.out.println("Prepared Statement Error : Table - User_Ratted_Movies ");
					e.printStackTrace();
				} finally {
					try {
						ps.close();
					} catch (SQLException e) {
						System.out.println("Prepared Statement Close Error : Table - User_Ratted_Movies ");
						e.printStackTrace();
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Data Populated to : Table - User_Ratted_Movies ");
	}

}
