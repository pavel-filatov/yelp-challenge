DROP KEYSPACE IF EXISTS yelp;

CREATE KEYSPACE yelp WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

CREATE TABLE yelp.friend (
    user_id text,
    friend_id text,
    PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE yelp.user (
    user_id text PRIMARY KEY,
    name text,
    review_count int,
    yelping_since timestamp,
    cool int,
    funny int,
    useful int,
    elite set<int>,
    fans int,
    average_stars double,
    compliment_hot int,
    compliment_more int,
    compliment_profile int,
    compliment_cute int,
    compliment_list int,
    compliment_note int,
    compliment_plain int,
    compliment_cool int,
    compliment_funny int,
    compliment_writer int,
    compliment_photos int
);

CREATE TABLE yelp.checkin (
    business_id text,
    date timestamp,
    PRIMARY KEY (business_id, date)
);

CREATE TABLE yelp.photo (
    business_id text,
    photo_id text,
    label text,
    caption text,
    PRIMARY KEY (business_id, label, photo_id)
);

CREATE TABLE yelp.tip (
    business_id text,
    user_id text,
    date timestamp,
    compliment_count int,
    text text,
    PRIMARY KEY (business_id, user_id, date)
);

CREATE TABLE yelp.review (
    business_id text,
    user_id text,
    review_id text,
    date timestamp,
    text text,
    stars double,
    cool int,
    funny int,
    useful int,
    PRIMARY KEY ((business_id, user_id), date, review_id)
);

CREATE TYPE yelp.hours (
    monday text,
    tuesday text,
    wednesday text,
    thursday text,
    friday text,
    saturday text,
    sunday text
);

CREATE TABLE yelp.business (
    business_id text PRIMARY KEY,
    state text,
    city text,
    categories set<text>,
    name text,
    address text,
    is_open int,
    latitude double,
    longitude double,
    postal_code text,
    review_count int,
    stars double,
    hours yelp.hours
);

CREATE TABLE yelp.business_attributes (
    business_id text PRIMARY KEY,
    "AcceptsInsurance" text,
    "AgesAllowed" text,
    "Alcohol" text,
    "Ambience" text,
    "BYOB" text,
    "BYOBCorkage" text,
    "BestNights" text,
    "BikeParking" text,
    "BusinessAcceptsBitcoin" text,
    "BusinessAcceptsCreditCards" text,
    "BusinessParking" text,
    "ByAppointmentOnly" text,
    "Caters" text,
    "CoatCheck" text,
    "Corkage" text,
    "DietaryRestrictions" text,
    "DogsAllowed" text,
    "DriveThru" text,
    "GoodForDancing" text,
    "GoodForKids" text,
    "GoodForMeal" text,
    "HairSpecializesIn" text,
    "HappyHour" text,
    "HasTV" text,
    "Music" text,
    "NoiseLevel" text,
    "Open24Hours" text,
    "OutdoorSeating" text,
    "RestaurantsAttire" text,
    "RestaurantsCounterService" text,
    "RestaurantsDelivery" text,
    "RestaurantsGoodForGroups" text,
    "RestaurantsPriceRange2" text,
    "RestaurantsReservations" text,
    "RestaurantsTableService" text,
    "RestaurantsTakeOut" text,
    "Smoking" text,
    "WheelchairAccessible" text,
    "WiFi" text,
);