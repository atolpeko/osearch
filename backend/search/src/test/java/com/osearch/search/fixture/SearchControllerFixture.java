package com.osearch.search.fixture;

public class SearchControllerFixture {
    public static final String URL = "/api/search/";
    public static final String SEARCH_STRING = "Work";
    public static final int OFFSET = 0;
    public static final int LIMIT = 2;
    public static final Integer NEXT_OFFSET = null;

    public static final String PAGE_1_URL = "$.pages.[0].url";
    public static final String PAGE_1_URL_VALUE = "https://www.linkedin.com";

    public static final String PAGE_2_URL = "$.pages.[1].url";
    public static final String PAGE_2_URL_VALUE = "https://www.glassdoor.es/";

    public static final String PAGE_1_TITLE = "$.pages.[0].title";
    public static final String PAGE_1_TITLE_VALUE = "LinkedIn";

    public static final String PAGE_2_TITLE = "$.pages.[1].title";
    public static final String PAGE_2_TITLE_VALUE = "Glassdoor";
}
