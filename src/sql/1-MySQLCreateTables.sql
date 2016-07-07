

-- Drop tables if exists --

DROP TABLE IF EXISTS UserProfile;
DROP TABLE IF EXISTS Route;
DROP TABLE IF EXISTS Tweet;
DROP TABLE IF EXISTS Stat;
DROP TABLE IF EXISTS Alert;




-- ---------- Table for validation queries from the connection pool. ----------

CREATE TABLE PingTable (foo CHAR(1));

-- ------------------------------ UserProfile ----------------------------------

CREATE TABLE UserProfile (
    usrId BIGINT NOT NULL AUTO_INCREMENT,
    loginName VARCHAR(30) COLLATE latin1_bin NOT NULL,
    enPassword VARCHAR(13) NOT NULL, 
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(40) NOT NULL, 
    email VARCHAR(60) NOT NULL,
    CONSTRAINT UserProfile_PK PRIMARY KEY (usrId),
    CONSTRAINT LoginNameUniqueKey UNIQUE (loginName)) 
    ENGINE = InnoDB;

CREATE INDEX UserProfileIndexByLoginName ON UserProfile (loginName);

CREATE TABLE Route (
    routeId BIGINT NOT NULL AUTO_INCREMENT,
    routeName VARCHAR(120) COLLATE latin1_bin NOT NULL,
    routeStart VARCHAR(120) COLLATE latin1_bin NOT NULL,
    routeEnd VARCHAR(120) COLLATE latin1_bin NOT NULL,
    kilometers BIGINT,
    positiveSlope BIGINT,
    negativeSlope BIGINT,
    numberOfHostels BIGINT,
	kmlFile VARCHAR(300),
    CONSTRAINT Route_PK PRIMARY KEY (routeId));
    
CREATE TABLE Tweet (
    tweetId BIGINT NOT NULL AUTO_INCREMENT,
    tweetText VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    tweetCreationDate TIMESTAMP COLLATE latin1_bin NOT NULL,
    retweetedId BIGINT,
    tweetFavourites BIGINT,
    tweetLatitude DOUBLE,
    tweetLongitude DOUBLE,
    tweetRetweets BIGINT,
	tweetUserId BIGINT,
	tweetIsFavourite BIT(1),
	tweetIsRetweeted BIT(1),
	tweetRouteId BIGINT,
	tweetSentiment TEXT,
    CONSTRAINT Tweet_PK PRIMARY KEY (tweetId),
	CONSTRAINT TweetRoute_FK FOREIGN KEY (tweetRouteId) REFERENCES Route(routeId));
	
CREATE TABLE Stat (
    statId BIGINT NOT NULL AUTO_INCREMENT,
    statDate DATE NOT NULL,
    statPNumber BIGINT NOT NULL,
    statNNumber BIGINT NOT NULL,
    statOpinionBalance BIGINT NOT NULL,
    statTipicalDeviation FLOAT(8,4),
    statNewNegative BIT(1),
    statOldMiddle FLOAT(8,4),
	statRouteId BIGINT,
    CONSTRAINT Stat_PK PRIMARY KEY (statId),
	CONSTRAINT StatRoute_FK FOREIGN KEY (statRouteId) REFERENCES Route(routeId));
	
CREATE TABLE Alert(
    alertId BIGINT NOT NULL AUTO_INCREMENT,
    alertDescription TEXT NOT NULL,
    alertDate TIMESTAMP COLLATE latin1_bin NOT NULL,
    alertCompleteDescription TEXT NOT NULL,
    alertRouteId BIGINT NOT NULL,
    alertStatId BIGINT NOT NULL,
    alertUserId BIGINT NOT NULL,
    CONSTRAINT Alert_PK PRIMARY KEY (alertId),
	CONSTRAINT AlertRoute_FK FOREIGN KEY (alertRouteId) REFERENCES Route(routeId),
	CONSTRAINT AlertStat_FK FOREIGN KEY (alertStatId) REFERENCES Stat(statId));
	
CREATE TABLE Parameter(
    parameterId BIGINT NOT NULL AUTO_INCREMENT,
    parameterKey TEXT NOT NULL,
    parameterValue BIGINT NOT NULL,
    parameterUserId BIGINT NOT NULL,
    parameterRouteId BIGINT NOT NULL,
    parameterRouteName TEXT NOT NULL,
    parameterDescription TEXT NOT NULL,
    CONSTRAINT Parameter_PK PRIMARY KEY (parameterId),
	CONSTRAINT ParameterUser_FK FOREIGN KEY (parameterUserId) REFERENCES UserProfile(usrId),
	CONSTRAINT ParameterRouteId_FK FOREIGN KEY (parameterRouteId) REFERENCES Route(routeId));