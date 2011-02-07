/*
 * Copyright 2005-2010 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kra.test.infrastructure;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.BaseFrame;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.FrameWindow;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlFileInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlImageInput;
import com.gargoylesoftware.htmlunit.html.HtmlInlineFrame;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * This class is the KRA base class for web tests
 */
public abstract class KcWebTestBase extends KcUnitTestBase {
    private static final String SECTION_ERROR_LABEL = "Errors found in this Section:";
    protected static final String HELP_PAGE_TITLE = "Kuali Research Administration Online Help";
    private static final String QUICKSTART_USER = "quickstart";

    public static final String PROTOCOL_AND_HOST = "http://127.0.0.1";
    
    protected WebClient webClient;
    private HtmlPage portalPage;
    
    { transactional = false; }

    /**
     * Web test setup overloading. Sets up Portal page access.
     *
     * @see org.kuali.kra.KraWebTestBase#setUp()
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        //many of our unit tests only work with IE 6 mode :-(
        webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_6);
        webClient.setRedirectEnabled(true);
        webClient.setPrintContentOnFailingStatusCode(true);
        webClient.setThrowExceptionOnFailingStatusCode(true);
        webClient.setThrowExceptionOnScriptError(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        
        setPortalPage(buildPageFromUrl(PROTOCOL_AND_HOST + ":" + getPort() + "/kc-dev/", "Kuali Portal Index"));
    }

    /**
     * Web test tear down overloading.
     *
     * @see org.kuali.kra.KraWebTestBase#tearDown()
     */
    @Override
    @After
    public void tearDown() throws Exception {
        try {
            super.tearDown();
        } finally {
            webClient.closeAllWindows();
            webClient = null;
        }
    }

    /**
     * Set the KRA Portal Web Page. The portal page is the starting point
     * for many (or all) HtmlUnit tests in order to simulate a user.
     *
     * @param portalPage <code>{@link HtmlPage}</code> instance for the portal page
     */
    protected final void setPortalPage(HtmlPage portalPage) {
        this.portalPage = portalPage;
    }

    /**
     * Get the KRA Portal Web Page. The portal page is the starting point
     * for many (or all) HtmlUnit tests in order to simulate a user.
     * @return the KRA Portal Web Page.
     * @throws IOException
     */
    protected final HtmlPage getPortalPage() throws IOException {
        return this.portalPage;
    }

    /**
     * Take a given <code>{@link String}</code> url and get a <code>{@link HtmlPage}</code> instance
     *  from it. This method actually overloads <code>{@link #buildPageFromUrl(URL, String)}</code>
     *
     * @param url <code>{@link String}</code> instance of url
     * @param title to compare against and verify the page is valid
     * @return HtmlPage
     * @throws IOException
     */
    protected final HtmlPage buildPageFromUrl(String url, String title) throws IOException {
        return buildPageFromUrl(new URL(url), title);
    }

    /**
     * Take a given <code>{@link URL}</code> url and get a <code>{@link HtmlPage}</code> instance
     *  from it.
     *
     * @param url <code>{@link Url}</code> instance of url
     * @param title to compare against and verify the page is valid
     * @return HtmlPage
     * @throws IOException
     */
    protected final HtmlPage buildPageFromUrl(URL url, String title) throws IOException {
        HtmlPage retval = (HtmlPage) webClient.getPage(url);
        assertEquals(title, retval.getTitleText());
        return retval;
    }

    /**
     * Simulate clicking on an HTML element in the web page.  To find
     * the HTML element, the following algorithm is used:
     * <ol>
     * <li>Search for a HTML element with an <b>id</b> attribute that matches the given id.</li>
     * <li>If not found, search for the first HTML element with a <b>name</b> attribute that matches.</li>
     * <li>If not found, search for the first HTML element with a <b>title</b> attribute that matches.</li>
     * </ol>
     *
     * Using any of the <b>clickOn()</b> methods is the preferred way to click on an HTML element
     * due to the Login process.  If the Login web page is encountered, the user will be
     * automatically logged in and next web page is returned.
     *
     * @param page the HTML web page.
     * @param id the <i>id</i> of the HTML element to click on.
     * @return the next web page after clicking on the HTML element.
     * @throws IOException
     */
    protected final HtmlPage clickOn(HtmlPage page, String id) throws IOException {
        return clickOn(page, id, null);
    }
    
    /**
     * Simulate clicking on an HTML element in the web page.  To find
     * the HTML element, the following algorithm is used:
     * <ol>
     * <li>Search for a HTML element with an <b>id</b> attribute that matches the given id.</li>
     * <li>If not found, search for the first HTML element with a <b>name</b> attribute that matches.</li>
     * <li>If not found, search for the first HTML element with a <b>title</b> attribute that matches.</li>
     * </ol>
     *
     * Using any of the <b>clickOn()</b> methods is the preferred way to click on an HTML element
     * due to the Login process.  If the Login web page is encountered, the user will be
     * automatically logged in and next web page is returned.
     *
     * @param page the HTML web page.
     * @param id the <i>id</i> of the HTML element to click on.
     * @return the next web page after clicking on the HTML element.
     * @throws IOException
     */
    protected final HtmlPage clickOnByName(HtmlPage page, String id, boolean startsWith) throws IOException {
        HtmlElement e = getElementByName(page, id, startsWith);
        return clickOn(e);
    }

    /**
     * Simulate clicking on an HTML element in the web page.  To find
     * the HTML element, the following algorithm is used:
     * <ol>
     * <li>Search for a HTML element with an <b>id</b> attribute that matches the given id.</li>
     * <li>If not found, search for the first HTML element with a <b>name</b> attribute that matches.</li>
     * <li>If not found, search for the first HTML element with a <b>title</b> attribute that matches.</li>
     * </ol>
     * If the <i>nextPageTitle</i>
     * is not null, the test case will fail if the next web page doesn't have the
     * expected title.
     *
     * Using any of the <b>clickOn()</b> methods is the preferred way to click on an HTML element
     * due to the Login process.  If the Login web page is encountered, the user will be
     * automatically logged in and next web page is returned.
     *
     * @param page the HTML web page.
     * @param id the <i>id</i> of the HTML element to click on.
     * @param nextPageTitle the expected title of the new web page (may be null).
     * @return the next web page after clicking on the HTML element.
     * @throws IOException
     */
    protected final HtmlPage clickOn(HtmlPage page, String id, String nextPageTitle) throws IOException {
        HtmlElement element = getElement(page, id);
        assertTrue(id + " not found. page " + page.asXml(), element != null);
        return clickOn(element, nextPageTitle);
    }

    /**
     * Simulate clicking on an HTML element in the web page.
     *
     * Using any of the <b>clickOn()</b> methods is the preferred way to click on an HTML element
     * due to the Login process.  If the Login web page is encountered, the user will be
     * automatically logged in and next web page is returned.
     *
     * @param element the HTML element to click on.
     * @return the next web page after clicking on the HTML element.
     * @throws IOException
     */
    protected final HtmlPage clickOn(HtmlElement element) throws IOException {
        return clickOn(element, null);
    }

    /**
     * Simulate clicking on an HTML element in the web page.
     *
     * Using any of the <b>clickOn()</b> methods is the preferred way to click on an HTML element
     * due to the Login process.  If the Login web page is encountered, the user will be
     * automatically logged in and next web page is returned.
     *
     * If the <i>nextPageTitle</i> is not null, the test case will fail if the next web page
     * doesn't have the expected title.
     *
     * @param element the HTML element to click on.
     * @param nextPageTitle the expected title of the new web page (may be null).
     * @return the next web page after clicking on the HTML element.
     * @throws IOException
     */
    protected final HtmlPage clickOn(HtmlElement element, String nextPageTitle) throws IOException {
        Page nextPage = element.click();
        assertTrue((nextPage != null) ? nextPage.getClass().getName() : "nextPage is null", nextPage instanceof HtmlPage);
        return confirmTitle((HtmlPage) nextPage, nextPageTitle);
    }
    
    private HtmlPage confirmTitle(HtmlPage page, String title) throws IOException {
        final HtmlPage postLogin = checkForLoginPage(page);
        
        if (title!= null) {
            HtmlPage match = getMatchingPage(postLogin, title);
            if (match == null) {
                fail("page not found: " + title + " page: \n" + postLogin.asXml());
            }
            return match;
        //just don't return the damn parent page
        } else if ("Kuali Portal Index".equals(postLogin.getTitleText()) && !getInnerPages(postLogin).isEmpty()) {
            return getInnerPages(postLogin).get(0);
        }
        return postLogin;
    }
    
    /**
     * Gets a page that matches the passed in title or null.  Will navigate all the inner pages.
     * @param page the page (may be a parent page)
     * @param title the title to find/match
     * @return a page or null
     */
    private final HtmlPage getMatchingPage(HtmlPage page, String title) {      
        if (page.getTitleText().equals(title)) {
            return page;
        }
        for (HtmlPage iPage : getInnerPages(page)) {
            HtmlPage match = getMatchingPage(iPage, title);
            if (match != null) {
                return match;
            }
        }
        return null;
    }

    /**
     * Simulate clicking on a Lookup icon.
     * This method is similar to the <b>clickOn()</b> methods except that it
     * is used for clicking on Lookup icon. This is because it is difficult to
     * find a Lookup HTML element based upon its full name, i.e. the name is
     * incredibly long and cryptic.  To find the Lookup HTML element, the name
     * attribute of the HTML lookup elements are examined to see if they contain
     * the given <i>id</i>.  As soon as a match is found, that HTML element
     * is clicked on.  Users should be sure to pick a part of the Lookup's name
     * that is unique for that Lookup.
     *
     * If the Lookup HTML element is not found, an assertion will cause
     * the test to fail.
     *
     * @param page the HTML web page.
     * @param tag identifies the Lookup HTML element.
     * @return the Lookup web page.
     * @throws IOException
     */
    protected final HtmlPage clickOnLookup(HtmlPage page, String tag) throws IOException {
        HtmlImageInput element = getLookup(page, tag);
        assertTrue("tag " + tag + " is null, page: " + page.asText(), element != null);

        return clickOn(element);
    }
    
    /**
     * Simulate clicking on the Expand all icon.
     *
     * If the Expand all HTML element is not found, an assertion will cause
     * the test to fail.
     *
     * @param page the HTML web page.
     * @return the expanded web page.
     * @throws IOException
     */
    protected final HtmlPage clickOnExpandAll(HtmlPage page) throws IOException {
        HtmlElement element = getElementByName(page, "methodToCall.showAllTabs");
        assertTrue("element " + "methodToCall.showAllTabs" + " is null, page: " + page.asText(), element != null);

        return clickOn(element);
    }
    
    /**
     * Simulate clicking on the Collapse all icon.
     *
     * If the Collapse all HTML element is not found, an assertion will cause
     * the test to fail.
     *
     * @param page the HTML web page.
     * @return the collapsed web page.
     * @throws IOException
     */
    protected final HtmlPage clickOnCollapseAll(HtmlPage page) throws IOException {
        HtmlElement element = getElementByName(page, "methodToCall.hideAllTabs");
        assertTrue("element " + "methodToCall.hideAllTabs" + " is null, page: " + page.asText(), element != null);

        return clickOn(element);
    }

    /**
     * Asserts that the given web page contains the given text.
     * @param page the HTML web page.
     * @param text the string to look for in the web page.
     */
    protected final void assertContains(HtmlPage page, String text) {
        assertContains(page, text, false);
    }
    
    /**
     * Asserts that the given web page does <b>not</b> contain the given text.
     * @param page the HTML web page.
     * @param text the string to look for in the web page.
     */
    protected final void assertDoesNotContain(HtmlPage page, String text) {
        assertDoesNotContain(page, text, false);
    }
    
    /**
     * Asserts that the given web page contains the given text.
     * @param page the HTML web page.
     * @param text the string to look for in the web page.
     * @param strictWhitespace whether to strictly match the whitespace characters in the text string
     */
    protected final void assertContains(HtmlPage page, String text, boolean strictWhitespace) {
        try {
            if (getElementByName(page, "methodToCall.showAllTabs") != null)
                page = clickOnExpandAll(page);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        if (!strictWhitespace) {
            final String regex = insertWhitespaceRegex(text);
            Pattern p = Pattern.compile(regex);
            assertTrue("page text:\n" + page.asText() + "\n does not contain:\n" + text + "\nas regex:\n" + regex, p.matcher(page.asText()).find()); 
        } else {
            assertTrue("page text:\n" + page.asText() + "\n does not contain:\n" + text, page.asText().contains(text));    
        }
    }
    
    /**
     * Asserts that the given web page does <b>not</b> contain the given text.
     * @param page the HTML web page.
     * @param text the string to look for in the web page.
     * @param strictWhitespace whether to strictly match the whitespace characters in the text string
     */
    protected final void assertDoesNotContain(HtmlPage page, String text, boolean strictWhitespace) {
        try {
            if (getElementByName(page, "methodToCall.showAllTabs") != null)
                page = clickOnExpandAll(page);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if (!strictWhitespace) {
            final String regex = insertWhitespaceRegex(text);
            Pattern p = Pattern.compile(regex);
            assertTrue("page text:\n" + page.asText() + "\n contains:\n" + text + "\nas regex:\n" + regex, !p.matcher(page.asText()).find());
        } else {
            assertTrue("page text:\n" + page.asText() + "\n contains:\n" + text, !page.asText().contains(text));    
        }
    }
    
    /**
     * Inserts regex to match against newlines and any amount of spaces.
     * @param text the text to insert regex
     * @return the modified text
     */
    private static String insertWhitespaceRegex(String text) {
        if (!text.contains(" ")) {
            return Pattern.quote(text.trim());
        }
        
        String regex = "(?m)";
        for (String token : text.trim().split(" ")) {
            if (!token.equals("") && !token.equals(" ")) {
                regex += Pattern.quote(token)+ "[\\s]*";
            }
        }
        return regex.substring(0, regex.length() - 5);
    }

    /**
     * Asserts that the given web page, as XML, contains the given text.
     * @param page the HTML web page.
     * @param text the string to look for in the web page.
     */
    protected final void assertXmlContains(HtmlPage page, String text) {
        assertTrue("page xml:\n" + page.asXml() + "\n does not contain:\n" + text, page.asXml().contains(text));
    }

    /**
     * Asserts that the given HTML element contains the given text.
     * @param element the HTML element.
     * @param text the string to look for in the HTML element.
     */
    protected final void assertContains(HtmlElement element, String text) {
        assertTrue("element text:\n" + element.asText() + "\n does not contain text:\n" + text, element.asText().contains(text));
    }

    /**
     * Asserts that the given HTML element does <b>not</b> contain the given text.
     * @param element the HTML element.
     * @param text the string to look for in the HTML element.
     */
    protected final void assertDoesNotContain(HtmlElement element, String text) {
        assertTrue("element text:\n" + element.asText() + "\n contains text:\n" + text, !element.asText().contains(text));
    }

    /**
     * Asserts that a Select control has the given number of options.
     * @param page the HTML web page.
     * @param elementId the value of the HTML element's id attribute.
     * @param size the number of options that must be in the list of options.
     */
    protected final void assertSelectOptionsSize(HtmlPage page, String elementId, int size) {
        HtmlElement element = page.getHtmlElementById(elementId);
        assertTrue("element " + elementId + " is null, page: " + page.asText(), element != null);

        if (element instanceof HtmlSelect) {
            HtmlSelect selectField = (HtmlSelect) element;
            assertEquals("select text:\n" + selectField.asText(), selectField.getOptionSize(), size);
        }
        else {
            assertTrue("Not a Select Field", false);
        }
    }

    /**
     * Performs a single value Lookup.  The following occurs on a lookup:
     * <ol>
     * <li>The Lookup icon is clicked on.</li>
     * <li>In the Lookup web page, the search button is clicked on.</li>
     * <li>The first item in the results is returned.</li>
     * <li>The web page resulting from clicking on "return value" is returned.</li>
     * </ol>
     * To find the Lookup HTML element, the name attribute of the HTML lookup
     * elements are examined to see if they contain the given <i>id</i>.  As soon as
     * a match is found, that HTML element is clicked on.  Users should be sure to pick
     * a part of the Lookup's name that is unique for that Lookup.
     *
     * The test will fail for any of the following reasons:
     * <ul>
     * <li>The HTML lookup element was not found.</li>
     * <li>There was no data returned in the search.</li>
     * </ul>
     *
     * @param page the original web page with the Lookup icon.
     * @param tag identifies the Lookup icon to click on.
     * @return the resulting web page.
     * @throws IOException
     */
    protected final HtmlPage lookup(HtmlPage page, String tag) throws IOException {
       return lookup(page, tag, null, null);
    }

    /**
     * Performs a single value Lookup.  The following occurs on a lookup:
     * <ol>
     * <li>The Lookup icon is clicked on.</li>
     * <li>In the Lookup web page, the given field is filled in with the given value.</li>
     * <li>In the Lookup web page, the search button is clicked on.</li>
     * <li>The first item in the results is returned.</li>
     * <li>The web page resulting from clicking on "return value" is returned.</li>
     * </ol>
     * To find the Lookup HTML element, the name attribute of the HTML lookup
     * elements are examined to see if they contain the given <i>id</i>.  As soon as
     * a match is found, that HTML element is clicked on.  Users should be sure to pick
     * a part of the Lookup's name that is unique for that Lookup.
     *
     * The test will fail for any of the following reasons:
     * <ul>
     * <li>The HTML lookup element was not found.</li>
     * <li>The search field HTML element was not found.</li>
     * <li>There was no data returned in the search.</li>
     * </ul>
     *
     * @param page the original web page with the Lookup icon.
     * @param tag identifies the Lookup icon to click on.
     * @param searchFieldId the id of the HTML field element (may be null).
     * @param searchValue the value to insert into the search field (may be null if searchFieldId is null).
     * @return the resulting web page.
     * @throws IOException
     */
    protected final HtmlPage lookup(HtmlPage page, String tag, String searchFieldId, String searchValue) throws IOException {

        return lookup(page, tag, searchFieldId, searchValue, Boolean.FALSE);
    }

    /**
     * Performs a single value Lookup.  The following occurs on a lookup:
     * <ol>
     * <li>The Lookup icon is clicked on.</li>
     * <li>In the Lookup web page, the given field is filled in with the given value.</li>
     * <li>In the Lookup web page, the search button is clicked on.</li>
     * <li>If the lookup does not return any results; the resulting no data found page is returned.</li>
     * <li>Otherwise the first item in the results is returned.</li>
     * <li>The web page resulting from clicking on "return value" is returned in later case.</li>
     * <li>Additional parameter checkNoDataFoundCase is passed as true if No Data Found Condition needs to be tested</li>
     * </ol>
     * To find the Lookup HTML element, the name attribute of the HTML lookup
     * elements are examined to see if they contain the given <i>id</i>.  As soon as
     * a match is found, that HTML element is clicked on.  Users should be sure to pick
     * a part of the Lookup's name that is unique for that Lookup.
     *
     * The test will fail for any of the following reasons:
     * <ul>
     * <li>The HTML lookup element was not found.</li>
     * <li>The search field HTML element was not found.</li>
     * </ul>
     *
     * @param page the original web page with the Lookup icon.
     * @param tag identifies the Lookup icon to click on.
     * @param searchFieldId the id of the HTML field element (may be null).
     * @param searchValue the value to insert into the search field (may be null if searchFieldId is null).
     * @param checkNoDataFoundCase the value to be passed to lookup method if no data found condition needs to be tested
     * @return the resulting web page.
     * @throws IOException
     */
    protected final HtmlPage lookup(HtmlPage page, String tag, String searchFieldId, String searchValue, Boolean checkNoDataFoundCase) throws IOException {    
        HtmlPage lookupPage = clickOnLookup(page, tag);

        if (searchFieldId != null) {
            assertTrue("searchValue is null", searchValue != null);
            setFieldValue(lookupPage, searchFieldId, searchValue);
        }

        // click on the search button
        HtmlImageInput searchBtn = (HtmlImageInput) getElement(lookupPage, "methodToCall.search", "search", "search");
        HtmlPage resultsPage = (HtmlPage) searchBtn.click();

        HtmlTable table = (HtmlTable) getElement(resultsPage, "row");
        HtmlAnchor anchor;
        final HtmlPage newPage;
        if(!checkNoDataFoundCase.booleanValue()){
            assertTrue("No data to return", table != null);
            HtmlTableBody body = table.getBodies().get(0);
            List<HtmlTableRow> rows = body.getRows();

            HtmlTableRow row = rows.get(0);
            List<HtmlTableCell> cells = row.getCells();
            HtmlTableCell cell = cells.get(0);
            anchor = (HtmlAnchor) getFirstChild(cell);
            newPage = (HtmlPage) anchor.click();
        }else{
            newPage = resultsPage;
        }
        return newPage;
    }

    /**
     * Performs a multi value Lookup.  The following occurs on a lookup:
     * <ol>
     * <li>The Lookup icon is clicked on.</li>
     * <li>In the Lookup web page, the search button is clicked on.</li>
     * <li>The "select all" button is clicked on.</li>
     * <li>The web page resulting from clicking on "return selected" is returned.</li>
     * </ol>
     * To find the Lookup HTML element, the name attribute of the HTML lookup
     * elements are examined to see if they contain the given <i>id</i>.  As soon as
     * a match is found, that HTML element is clicked on.  Users should be sure to pick
     * a part of the Lookup's name that is unique for that Lookup.
     *
     * The test will fail if the Lookup HTML element is not found.
     *
     * @param page the original web page with the Lookup icon.
     * @param tag identifies the Lookup icon to click on.
     * @return the resulting web page.
     * @throws IOException
     */
    protected final HtmlPage multiLookup(HtmlPage page, String tag) throws IOException {
        return multiLookup(page, tag, null, null);
    }

    /**
     * Performs a multi value Lookup.  The following occurs on a lookup:
     * <ol>
     * <li>The Lookup icon is clicked on.</li>
     * <li>The search field is filled in with the given search value.</li>
     * <li>In the Lookup web page, the search button is clicked on.</li>
     * <li>The "select all" button is clicked on.</li>
     * <li>The web page resulting from clicking on "return selected" is returned.</li>
     * </ol>
     * To find the Lookup HTML element, the name attribute of the HTML lookup
     * elements are examined to see if they contain the given <i>id</i>.  As soon as
     * a match is found, that HTML element is clicked on.  Users should be sure to pick
     * a part of the Lookup's name that is unique for that Lookup.
     *
     * The test will fail for any of the following reasons:
     * <ul>
     * <li>The HTML lookup element was not found.</li>
     * <li>The search field HTML element was not found.</li>
     * </ul>
     *
     * @param page the original web page with the Lookup icon.
     * @param tag identifies the Lookup icon to click on.
     * @param searchFieldId the id of the HTML field element (may be null).
     * @param searchValue the value to insert into the search field (may be null if searchFieldId is null).
     * @return the resulting web page.
     * @throws IOException
     */
    protected HtmlPage multiLookup(HtmlPage page, String tag, String searchFieldId, String searchValue) throws IOException {
        HtmlPage lookupPage = clickOnLookup(page, tag);

        if (searchFieldId != null) {
            assertTrue("searchValue is null", searchValue != null);
            setFieldValue(lookupPage, searchFieldId, searchValue);
        }

        // click on the search button
        HtmlImageInput searchBtn = (HtmlImageInput) getElement(lookupPage, "methodToCall.search", "search", "search");
        HtmlPage resultsPage = (HtmlPage) searchBtn.click();
        
        HtmlImageInput selectAllBtn = (HtmlImageInput) getElement(resultsPage, "methodToCall.selectAll.(::;true;::).x", null, null);
        HtmlPage selectedPage = (HtmlPage) selectAllBtn.click();
        setCheckboxes(selectedPage);

        HtmlImageInput returnAllBtn = (HtmlImageInput) getElement(selectedPage, "methodToCall.prepareToReturnSelectedResults.(::;false;::)", null, null);
        if (returnAllBtn == null) {
            returnAllBtn = (HtmlImageInput) getElement(selectedPage, "methodToCall.prepareToReturnSelectedResults.(::;true;::)", null, null);            
        }
        //HtmlPage returnPage = (HtmlPage) returnAllBtn.click();
        HtmlPage returnPage = clickOn(returnAllBtn);
        return returnPage;
    }

    /*
     * This is a hack.  When the "select all" button is clicked on by a real
     * user using a browser, the returned checkbox html elements have the
     * value attribute set to "checked".  But for HTML Unit, the value is an
     * empty string.  Therefore, we have to programmatically set the value
     * to "checked".
     */
    private void setCheckboxes(HtmlPage page) {
        List<HtmlElement> elements = getInputElements(page, "checkbox");
        for (HtmlElement element : elements) {
            element.setAttribute("value", "checked");
        }
    }

    /*
     * Get all of the HTML Input elements of a given type.
     */
    private List<HtmlElement> getInputElements(HtmlPage page, String type) {
        List<HtmlElement> elements = new ArrayList<HtmlElement>();

        for (HtmlElement e : page.getHtmlElementDescendants()) {
            if (StringUtils.equalsIgnoreCase("input", e.getTagName())) {
                String value = e.getAttribute("type");
                if (StringUtils.equalsIgnoreCase(type, value)) {
                    elements.add(e);
                }
            }
        }
        return elements;
    }

    /**
     * Set the value of a control field.  The control field must be a
     * text, text area, hidden, checkbox, radio button, or single-select field.
     *
     * For a checkbox field, the only legal values are "on" and "off".
     *
     * The test will fail for any of the following reasons:
     * <ul>
     * <li>The HTML control element was not found.</li>
     * <li>The control is not a text, text area, hidden, checkbox, radio button, or select field.</li>
     * </ul>
     *
     * @param page the HTML web page.
     * @param fieldId the id of the HTML element.
     * @param fieldValue the value to set the control to.
     */
    protected final void setFieldValue(HtmlPage page, String fieldId, String fieldValue) {
        HtmlElement element = getElement(page, fieldId);
        assertTrue("element " + fieldId + " is null, page: " + page.asText(), element != null);
        
        if(element == null &&  page != null && LOG.isDebugEnabled()) {
            LOG.debug(createExpectedFieldNotFoundMessage(page, fieldId));
            LOG.debug(page.asXml());
        }
        assertTrue(createExpectedFieldNotFoundMessage(page, fieldId), element != null);

        if (element instanceof HtmlTextInput) {
            HtmlTextInput textField = (HtmlTextInput) element;
            textField.setValueAttribute(fieldValue);
        }
        else if (element instanceof HtmlTextArea) {
            HtmlTextArea textAreaField = (HtmlTextArea) element;
            textAreaField.setText(fieldValue);
        }
        else if (element instanceof HtmlHiddenInput) {
            HtmlHiddenInput hiddenField = (HtmlHiddenInput) element;
            hiddenField.setValueAttribute(fieldValue);
        }
        else if (element instanceof HtmlSelect) {
            HtmlSelect selectField = (HtmlSelect) element;
            try {
                selectField.setSelectedAttribute(fieldValue, true);
            } catch (IllegalArgumentException e) {
                Assert.fail("select element [" + element.asText() + "] " + e.getMessage());
            }
        }
        else if (element instanceof HtmlCheckBoxInput) {
            HtmlCheckBoxInput checkboxField = (HtmlCheckBoxInput) element;
            if (fieldValue.equals("on")) {
                checkboxField.setChecked(true);
            }
            else if (fieldValue.equals("off")) {
                checkboxField.setChecked(false);
            }
            else {
                assertTrue("Invalid checkbox value", false);
            }
        }
        else if (element instanceof HtmlFileInput) {
            HtmlFileInput fileInputField = (HtmlFileInput) element;
            fileInputField.setValueAttribute(fieldValue);
        }
        else if (element instanceof HtmlRadioButtonInput) {
            List<HtmlElement> elements = getAllElementsByName(page, fieldId, false);
            for (HtmlElement child : elements) {
                assertTrue(child.getClass().getName(), child instanceof HtmlRadioButtonInput);
                HtmlRadioButtonInput radioBtn = (HtmlRadioButtonInput) child;
                if (radioBtn.getValueAttribute().equals(fieldValue)) {
                    radioBtn.setChecked(true);
                    break;
                }
            }
        }
        else {
            if(page != null && LOG.isDebugEnabled()) {
                LOG.debug(String.format("Unknown control field %s on page titled %s", page.getTitleText(), fieldId));
                LOG.debug(page.asXml());
            }
            fail("Unknown control field: " + fieldId);
        }
    }

    /**
     * Gets the current value of a control field.
     *
     * For a checkbox field, the only legal values are "on" and "off".
     *
     * The test will fail for any of the following reasons:
     * <ul>
     * <li>The HTML control element was not found.</li>
     * <li>The control is not a text, text area, hidden, checkbox, radio button, or select field.</li>
     * </ul>
     *
     * @param page the HTML web page.
     * @param fieldId the id of the HTML element.
     * @return the current value.
     */
    protected final String getFieldValue(HtmlPage page, String fieldId) {
        String fieldValue = null;

        HtmlElement element = getElement(page, fieldId);
        assertTrue(fieldId + " not found", element != null);

        if (element instanceof HtmlTextInput) {
            HtmlTextInput textField = (HtmlTextInput) element;
            fieldValue = textField.getValueAttribute();
        }
        else if (element instanceof HtmlTextArea) {
            HtmlTextArea textAreaField = (HtmlTextArea) element;
            fieldValue = textAreaField.getText();
        }
        else if (element instanceof HtmlHiddenInput) {
            HtmlHiddenInput hiddenField = (HtmlHiddenInput) element;
            fieldValue = hiddenField.getValueAttribute();
        }
        else if (element instanceof HtmlSelect) {
            HtmlSelect selectField = (HtmlSelect) element;
            fieldValue = (selectField.getSelectedOptions().get(0)).getValueAttribute();
        }
        else if (element instanceof HtmlCheckBoxInput) {
            HtmlCheckBoxInput checkboxField = (HtmlCheckBoxInput) element;
            fieldValue = checkboxField.isChecked() ? "on" : "off";
        }
        else if (element instanceof HtmlRadioButtonInput) {
            List<HtmlElement> elements = getAllElementsByName(page, fieldId, false);
            for (HtmlElement child : elements) {
                assertTrue(child.getClass().getName(), child instanceof HtmlRadioButtonInput);
                HtmlRadioButtonInput radioBtn = (HtmlRadioButtonInput) child;
                if (radioBtn.isChecked()) {
                    fieldValue = radioBtn.getValueAttribute();
                    break;
                }
            }
        }
        else {
            assertTrue("Unknown control field", false);
        }

        return fieldValue;
    }

    /**
     * Gets the default value of a control field.
     *
     * For a checkbox field, the only legal values are "on" and "off".
     *
     * The test will fail for any of the following reasons:
     * <ul>
     * <li>The HTML control element was not found.</li>
     * <li>The control is not a text, text area, hidden, checkbox, radio button, or select field.</li>
     * </ul>
     *
     * @param page the HTML web page.
     * @param fieldId the id of the HTML element.
     * @return the default value.
     */
    protected final String getDefaultFieldValue(HtmlPage page, String fieldId) {
        String fieldValue = null;

        HtmlElement element = getElement(page, fieldId);
        assertTrue("element " + fieldId + " is null, page: " + page.asText(), element != null);

        if (element instanceof HtmlTextInput) {
            HtmlTextInput textField = (HtmlTextInput) element;
            fieldValue = textField.getDefaultValue();
        }
        else if (element instanceof HtmlTextArea) {
            HtmlTextArea textAreaField = (HtmlTextArea) element;
            fieldValue = textAreaField.getDefaultValue();
        }
        else if (element instanceof HtmlHiddenInput) {
            HtmlHiddenInput hiddenField = (HtmlHiddenInput) element;
            fieldValue = hiddenField.getDefaultValue();
        }
        else if (element instanceof HtmlSelect) {
            HtmlSelect selectField = (HtmlSelect) element;
            fieldValue = selectField.getDefaultValue();
        }
        else if (element instanceof HtmlCheckBoxInput) {
            HtmlCheckBoxInput checkboxField = (HtmlCheckBoxInput) element;
            fieldValue = checkboxField.isDefaultChecked() ? "on" : "off";
        }
        else if (element instanceof HtmlRadioButtonInput) {
            List<HtmlElement> elements = getAllElementsByName(page, fieldId, false);
            for (HtmlElement child : elements) {
                assertTrue(child.getClass().getName(), child instanceof HtmlRadioButtonInput);
                HtmlRadioButtonInput radioBtn = (HtmlRadioButtonInput) child;
                if (radioBtn.isDefaultChecked()) {
                    fieldValue = radioBtn.getValueAttribute();
                    break;
                }
            }
        }
        else {
            assertTrue("Unknown control field", false);
        }

        return fieldValue;
    }

    /**
     * Checks all of the Help hyperlinks on a web page.  Each hyperlink
     * with a <b>helpWindow</b> target is clicked on to verify that the
     * the Help web page is displayed.  The contents of the help page is
     * not examined.  The test will only fail if a Help hyperlink does
     * not bring up a Help web page.  This is useful to verify that there
     * are no broken links.
     *
     * @param page the HTML page to check.
     * @throws IOException
     */
    protected final void checkHelpLinks(HtmlPage page) throws IOException {
        List<HtmlAnchor> anchors = findHelpLinks(page);
        for (HtmlAnchor anchor : anchors) {
            HtmlPage helpPage = (HtmlPage) anchor.click();
            assertEquals(HELP_PAGE_TITLE, helpPage.getTitleText());
        }
    }

    /**
     * Checks the Expanded Text Area.  Many text area controls have a corresponding
     * expanded text area icon (pencil) for displaying another web page with a larger
     * text area box.  This method does the following to verify that the expanded text
     * area feature is working properly.
     * Note: Pencil icon's position should be next to TextArea with no other elements in between,
     * in case if element/elements exist, use variant.
     * <ol>
     * <li>The text area is set to the <i>text1</i> value.</li>
     * <li>The Expanded Text Area icon is clicked on.</li>
     * <li>The text in the expanded text area web page is examined to verify
     *     that it is equal to <i>text1</i>.</li>
     * <li>The text area is changed to <i>text2</i>.</li>
     * <li>The "save" button is clicked on.</li>
     * <li>The resulting web page is examined to verify that the original
     *     text area has changed to the value of <i>text2</i>.</li>
     * </ol>
     *
     * The test will fail for any of the following reasons:
     * <ul>
     * <li>The HTML text area element was not found.</li>
     * <li>The Expanded Text Area icon HTML element was not found.</li>
     * <li>The setting of <i>text1</i> did not transfer to the Expanded Text Area web page.</li>
     * <li>The saving of <i>text2</i> did not transfer to the original text area.</li>
     * </ul>
     *
     * @param page the HTML web page with the text area control.
     * @param id identifies the text area (not its corresponding icon).
     * @param text1 the string to set the original text area to.
     * @param text2 the string to set in the Expanded Text Area web page.
     * @return the resulting web page from saving <i>text2</i>.
     * @throws IOException
     */
    protected final HtmlPage checkExpandedTextArea(HtmlPage page, String id, String text1, String text2) throws IOException {

        return checkExpandedTextArea(page, id, null, text1, text2);
        
    }
    
    /**
     * This method is variant, it explicitly checks for Pencil Icon button's Id as provided by method @param iconTextAreaId.
     * @param page the HTML web page with the text area control.
     * @param textAreaId identifies the text area.
     * @param iconTextAreaId identifies the pencil icon text area.
     * @param text1 the string to set the original text area to.
     * @param text2 the string to set in the Expanded Text Area web page.
     * @return the resulting web page from saving <i>text2</i>.
     * @throws IOException
     */
    protected final HtmlPage checkExpandedTextArea(HtmlPage page, String textAreaId, String iconTextAreaId, String text1, String text2) throws IOException {
        boolean javascriptEnabled = webClient.isJavaScriptEnabled();

        webClient.setJavaScriptEnabled(false);

        setFieldValue(page, textAreaId, text1);

        HtmlElement field = getElement(page, textAreaId);
        assertTrue("field is null", field != null);
        
        HtmlElement btn =  this.getNextSibling(field, "input");
        assertTrue("btn is null", btn != null);
        
        //If pencil icon is not located next TextArea following alternate method will be used if iconTextAreaId is not null.
        if(null == btn && null != iconTextAreaId) {
            HtmlElement expandedField = getElement(page, iconTextAreaId);
            if(null != expandedField) {
                btn = expandedField;
            }
        }
        
        assertTrue(btn != null);

        HtmlPage textPage = clickOn(btn);

        assertEquals(text1, getFieldValue(textPage, textAreaId));

        setFieldValue(textPage, textAreaId, text2);
        HtmlPage returnPage = clickOn(textPage, "return");

        assertEquals(text2, getFieldValue(returnPage, textAreaId));

        webClient.setJavaScriptEnabled(javascriptEnabled);

        return returnPage;
    }
    
    /**
     * Find out if the page contains one or more errors.
     * @param page the web page
     * @return true if there is an error; otherwise false
     */
    protected final boolean hasError(HtmlPage page) {
        try {
            if (getElementByName(page, "methodToCall.showAllTabs") != null)
                page = clickOnExpandAll(page);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return page.asText().contains("error(s) found on page") || page.asText().contains("Errors Found in Document")
        || page.asText().contains("Kuali :: Incident Report");
    }

    /**
     * Gets the error messages for a specific panel.  If an operation
     * results in an error, those errors are displayed in specific panel,
     * i.e. tab.  Each panel is contained within an HTML div tag that has
     * a unique id, i.e. the HTML id attribute.
     *
     * @param page the HTML web page.
     * @param panelId the unique id of the panel.
     * @return the list of error strings (may be empty).
     */
    protected final List<String> getErrors(HtmlPage page, String panelId) {
        try {
            if (getElementByName(page, "methodToCall.showAllTabs") != null)
                page = clickOnExpandAll(page);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        List<String> errors = new ArrayList<String>();

        HtmlElement panelDiv = getElement(page, panelId);
        assertTrue("panelDiv is null", panelDiv != null);

        List<HtmlElement> errorElements = getErrorElementsByStyle(panelDiv, "display:list-item");
        for (HtmlElement element : errorElements) {
            errors.add(element.asText());
        }

        return errors;
    }

    /**
     * Determines if any of the errors contains the given text string.
     *
     * @param errors the list of errors.
     * @param text the string to compare against.
     * @return true if any of errors contains the text string; otherwise false.
     */
    protected final boolean containsError(List<String> errors, String text) {
        for (String error : errors) {
            if (error.contains(text)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for the Login web page.  The Login web page can be
     * obtained due to a request for another web page.  This method
     * logs the user into the system and returns the expected web page.
     *
     * @param page the HTML web page.
     * @return the same HTML web page or the one resulting from the login.
     * @throws IOException
     */
    private HtmlPage checkForLoginPage(HtmlPage page) throws IOException {
        HtmlPage newPage = page;
        
        if (newPage.getTitleText().equals("Login")) {
            HtmlForm form = page.getForms().get(0);
            setFieldValue(page, "__login_user", getLoginUserName());
            HtmlSubmitInput loginBtn = (HtmlSubmitInput) form.getInputByValue("Login");
            //boolean javascriptEnabled = webClient.isJavaScriptEnabled();
            //webClient.setThrowExceptionOnScriptError(false);
            newPage = (HtmlPage) loginBtn.click();
            if (newPage.getTitleText().equals("Login")) {
                newPage = (HtmlPage) loginBtn.click();
            }
            //webClient.setThrowExceptionOnScriptError(true);
        }
        return newPage;
    }
    
    /**
     * Get the userName to login with.  May be overridden
     * by subclasses to log in as a different user.
     * @return
     */
    protected String getLoginUserName() {
        return QUICKSTART_USER;
    }
    
    /**
     * Logs out and then logs in a new user in via the backdoor.
     * @param username the user's username
     * @return the portal page
     * @throws Exception
     */
    protected void backdoorLogin(String username) throws Exception {
        HtmlPage portal = this.getPortalPage();
        HtmlElement element = getElement(portal, "backdoorId");
        if (element == null) {
            portal = clickOn(portal, "Researcher");
        }
        setFieldValue(portal, "backdoorId", username);
        clickOn(portal, "imageField");
    }
    
    /**
     * Logs in via the backdoor as "jtester".
     * @return the portal page
     * @throws Exception
     */
    protected void loginAsTester() throws Exception {
        backdoorLogin("jtester");
    }

    /**
     * Gets an HTML element in the web page.
     *
     * Since some HTML elements don't use the id attribute, those HTML elements
     * can found by searching for elements with the given values for the <b>name</b>,
     * <b>value</b>, and <b>title</b> attributes.  The first HTML element that
     * matches is returned.
     *
     * HTML web pages may contain Inline Frames (iframes) which are not expanded
     * within HtmlUnit.  The inline frames contain inner web pages that must
     * also be searched.
     *
     * @param page the HTML web page.
     * @param name the value for the name attribute (may be null).
     * @param value the value for the value attribute (may be null).
     * @param title the value for the title attribute (may be null).
     * @return the HTML element or null if not found.
     */
    protected final HtmlElement getElement(HtmlPage page, String name, String value, String title) {
        HtmlElement element = getElement(page.getDocumentElement(), name, value, title);

        if (element == null) {
            List<HtmlPage> innerPages = getInnerPages(page);
            for (HtmlPage innerPage : innerPages) {
                element = getElement(innerPage, name, value, title);
                if (element != null) {
                    break;
                }
            }
        }

        return element;
    }

    /**
     * Gets an HTML element in an HTML element.
     *
     * Since some HTML elements don't use the id attribute, those HTML elements
     * can found by searching for elements with the given values for the <b>name</b>,
     * <b>value</b>, and <b>title</b> attributes.  The first HTML element that
     * matches is returned.
     *
     * @param element the HTML element.
     * @param name the value for the name attribute (may be null).
     * @param value the value for the value attribute (may be null).
     * @param title the value for the title attribute (may be null).
     * @return the HTML element or null if not found.
     */
    private HtmlElement getElement(HtmlElement element, String name, String value, String title) {
        for (HtmlElement child : element.getHtmlElementDescendants()) {
            String nameValue = child.getAttribute("name");
            String valueValue = child.getAttribute("value");
            String titleValue = child.getAttribute("title");
            if ((name == null || name.equals(nameValue)) &&
                (value == null || value.equals(valueValue)) &&
                (title == null || title.equals(titleValue))) {
                return child;
            }
        }
        return null;
    }

    /**
     * Gets an HTML element in the web page.
     *
     * To find the HTML element, the following algorithm is used:
     * <ol>
     * <li>Search for a HTML element with an <b>id</b> attribute that matches the given id.</li>
     * <li>If not found, search for the first HTML element with a <b>name</b> attribute that matches.</li>
     * <li>If not found, search for the first HTML element with a <b>title</b> attribute that matches.</li>
     * </ol>
     *
     * @param page the HTML web page to search.
     * @param id the id of the HTML attribute.
     * @return the HTML element or null if not found.
     */
    protected final HtmlElement getElement(HtmlPage page, String id) {
        HtmlElement element = getElementById(page, id);
        if (element == null) {
            element = getElementByName(page, id);
            if (element == null) {
                element = getElementByTitle(page, id);
            }
        }
        return element;
    }

    /**
     * Gets an HTML element in the web page.  Searches the web page for
     * an HTML element whose id attribute matches the given id.
     *
     * HTML web pages may contain Inline Frames (iframes) which are not expanded
     * within HtmlUnit.  The inline frames contain inner web pages that must
     * also be searched.
     *
     * @param page the HTML web page to search.
     * @param id the id to search for.
     * @return the HTML element or null if not found.
     */
    protected final HtmlElement getElementById(HtmlPage page, String id) {
        HtmlElement element = null;
        try {
            element = page.getHtmlElementById(id);
        } catch (ElementNotFoundException ex) {
            List<HtmlPage> innerPages = getInnerPages(page);
            for (HtmlPage innerPage : innerPages) {
                element = getElementById(innerPage, id);
                if (element != null) {
                    break;
                }
            }
        }
        return element;
    }

    /**
     * Gets an HTML element in the web page.  Searches the web page for
     * the first HTML element whose name attribute matches the given name.
     *
     * HTML web pages may contain Inline Frames (iframes) which are not expanded
     * within HtmlUnit.  The inline frames contain inner web pages that must
     * also be searched.
     *
     * @param page the HTML web page to search.
     * @param name the name to search for.
     * @return the HTML element or null if not found.
     */
    protected final HtmlElement getElementByName(HtmlPage page, String name) {
        return getElementByName(page, name, false);
    }

    /**
     * Gets an HTML element in a parent HTML element.  Searches the parent HTML
     * element for the first HTML element whose name attribute matches the given name.
     *
     * @param element the parent HTML element to search.
     * @param name the name to search for.
     * @return the HTML element or null if not found.
     */
    protected final HtmlElement getElementByName(HtmlElement element, String name) {
        return getElementByName(element, name, false);
    }

    /**
     * Gets an HTML element in the web page.  Searches the web page for
     * the first HTML element whose name attribute matches the given name.
     *
     * HTML web pages may contain Inline Frames (iframes) which are not expanded
     * within HtmlUnit.  The inline frames contain inner web pages that must
     * also be searched.
     *
     * @param page the HTML web page to search.
     * @param name the name to search for.
     * @param startsWith if true, only match against the start of the name.
     * @return the HTML element or null if not found.
     */
    protected final HtmlElement getElementByName(HtmlPage page, String name, boolean startsWith) {
        HtmlElement element = getElementByName(page.getDocumentElement(), name, startsWith);

        if (element == null) {
            List<HtmlPage> innerPages = getInnerPages(page);
            for (HtmlPage innerPage : innerPages) {
                element = getElementByName(innerPage, name, startsWith);
                if (element != null) {
                    break;
                }
            }
        }

        return element;
    }

    /**
     * Gets an HTML element in a parent HTML element.  Searches the parent HTML
     * element for the first HTML element whose name attribute matches the given name.
     *
     * @param element the parent HTML element to search.
     * @param name the name to search for.
     * @param startsWith if true, only match against the start of the name.
     * @return the HTML element or null if not found.
     */
    protected final HtmlElement getElementByName(HtmlElement element, String name, boolean startsWith) {
        for (HtmlElement e : element.getHtmlElementDescendants()) {
            String value = e.getAttribute("name");
            if (!startsWith && name.equals(value)) {
                return e;
            } else if (startsWith && value != null && value.startsWith(name)) {
                return e;
            }
        }
        return null;
    }
    
    /**
     * Gets an HTML element in the web page.  Searches the web page for
     * the first HTML element whose name attribute ends with the given name.
     *
     * HTML web pages may contain Inline Frames (iframes) which are not expanded
     * within HtmlUnit.  The inline frames contain inner web pages that must
     * also be searched.
     *
     * @param page the HTML web page to search.
     * @param name the name to search for.
     * @param startsWith if true, only match against the start of the name.
     * @return the HTML element or null if not found.
     */
    protected final HtmlElement getElementByNameEndsWith(HtmlPage page, String name) {
        HtmlElement element = getElementByNameEndsWith(page.getDocumentElement(), name);

        if (element == null) {
            List<HtmlPage> innerPages = getInnerPages(page);
            for (HtmlPage innerPage : innerPages) {
                element = getElementByNameEndsWith(innerPage, name);
                if (element != null) {
                    break;
                }
            }
        }

        return element;
    }

    /**
     * Gets an HTML element in a parent HTML element.  Searches the parent HTML
     * element for the first HTML element whose name attribute ends with the given name.
     *
     * @param element the parent HTML element to search.
     * @param name the name to search for.
     * @param startsWith if true, only match against the start of the name.
     * @return the HTML element or null if not found.
     */
    protected final HtmlElement getElementByNameEndsWith(HtmlElement element, String name) {
        for (HtmlElement e : element.getHtmlElementDescendants()) {
            String value = e.getAttribute("name");
            if (value.endsWith(name)) {
                return e;
            }
        }
        return null;
    }
    
    /**
     * Gets an HTML element in the web page.  Searches the web page for
     * the first HTML element whose title attribute matches the given title.
     *
     * HTML web pages may contain Inline Frames (iframes) which are not expanded
     * within HtmlUnit.  The inline frames contain inner web pages that must
     * also be searched.
     *
     * @param page the HTML web page to search.
     * @param title the title to search for.
     * @return the HTML element or null if not found.
     */
    protected final HtmlElement getElementByTitle(HtmlPage page, String title) {
        HtmlElement element = getElementByTitle(page.getDocumentElement(), title);

        if (element == null) {
            List<HtmlPage> innerPages = getInnerPages(page);
            for (HtmlPage innerPage : innerPages) {
                element = getElementByTitle(innerPage, title);
                if (element != null) {
                    break;
                }
            }
        }
        return element;
    }

    /**
     * Gets an HTML element in a parent HTML element.  Searches the parent HTML
     * element for the first HTML element whose title attribute matches the given title.
     *
     * @param element the parent HTML element to search.
     * @param title the title to search for.
     * @return the HTML element or null if not found.
     */
    protected final HtmlElement getElementByTitle(HtmlElement element, String title) {
        for (HtmlElement e : element.getHtmlElementDescendants()) {
            String value = e.getAttribute("title");
            if (title.equals(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Gets an HTML element in a parent HTML element.  Searches the parent HTML
     * element for the first HTML element whose class attribute matches the given classname.
     *
     * @param element the parent HTML element to search.
     * @param classname the classname to search for.
     * @return the HTML element or null if not found.
     */
    protected final HtmlElement getElementByClass(HtmlElement element, String classname) {
        for (HtmlElement e : element.getHtmlElementDescendants()) {
            String value = e.getAttribute("class");
            if (classname.equals(value)) {
                return e;
            }
        }
        return null;
    }
    
    
    protected final List<HtmlElement> getErrorElementsByStyle(HtmlElement element, String stylePrefix) {
        List<HtmlElement> errorElements = new ArrayList<HtmlElement>();
        for (HtmlElement e : element.getHtmlElementDescendants()) {
            String value = e.getAttribute("style");
            if (value.startsWith(stylePrefix)) {
                errorElements.add(e);
            }
        }
        return errorElements;
    }

    /**
     * Gets a Lookup HTML element.  The searching for Lookup HTML elements
     * is a special case.  This is because it lacks an id attribute and because
     * the value of its name attribute is extremely long and cryptic. To find
     * a Lookup element, the value of the name attribute is examined to see if
     * it contains the given id.  Lookup HTML elements always contain some data
     * in the name attribute that is specific to the Lookup.
     *
     * HTML web pages may contain Inline Frames (iframes) which are not expanded
     * within HtmlUnit.  The inline frames contain inner web pages that must
     * also be searched.
     *
     * @param page the HTML web page to search.
     * @param tag the tag to compare against Lookup HTML name attributes.
     * @return the Lookup's HTML element or null if not found.
     */
    protected final HtmlImageInput getLookup(HtmlPage page, String tag) {
        HtmlImageInput element = getLookup(page.getDocumentElement(), tag);

        List<HtmlPage> innerPages = getInnerPages(page);
        for (Iterator<HtmlPage> page_it = innerPages.iterator(); page_it.hasNext() && element == null;) {
            HtmlPage innerPage = page_it.next();
            element = getLookup(innerPage, tag);
        }

        return element;
    }

    /**
     * Gets a Lookup HTML element.  The searching for Lookup HTML elements
     * is a special case.  This is because it lacks an id attribute and because
     * the value of its name attribute is extremely long and cryptic. To find
     * a Lookup element, the value of the name attribute is examined to see if
     * it contains the given id.  Lookup HTML elements always contain some data
     * in the name attribute that is specific to the Lookup.
     *
     * @param element the parent HTML element to search.
     * @param tag the tag to compare against Lookup HTML name attributes.
     * @return the Lookup's HTML element or null if not found.
     */
    private final HtmlImageInput getLookup(HtmlElement element, String tag) {
        for (HtmlElement e : element.getHtmlElementDescendants()) {
            if (e instanceof HtmlImageInput) {
                String name = e.getAttribute("name");
                LOG.info("Found name attribute " + name);
                if (name != null && name.startsWith("methodToCall.performLookup") && name.contains(tag)) {
                    return (HtmlImageInput) e;
                }
            }
        }
        return null;
    }

    /**
     * Gets a HTML Table element.  The search begins within the
     * first HTML element identified by <i>id</i>.  If there are
     * multiple tables in the HTML element, only one is returned
     * and it is undefined which one it will be.
     *
     * @param page the HTML web page.
     * @param id identifies the HTML element to begin searching in.
     * @return the HTML table or null if not found.
     */
    protected final HtmlTable getTable(HtmlPage page, String id) {
        HtmlTable table = null;
        HtmlElement element = getElement(page, id);
        assertTrue("element " + id + " is null, page: " + page.asText(), element != null);

        if (element instanceof HtmlTable) {
            table = (HtmlTable) element;
        }
        else {
            for (HtmlElement e : element.getHtmlElementDescendants()) {
                if (e instanceof HtmlTable) {
                    table = (HtmlTable) e;
                    break;
                }
            }
        }
        return table;
    }

    /**
     * Gets an Anchor (hyperlink) element whose HREF attribute string contains
     * the given tag string.
     *
     * @param page the web page.
     * @param tag the string to look for in HREF.
     * @return the HTML Anchor element or null if not found.
     */
    protected final HtmlAnchor getAnchor(HtmlPage page, String tag) {
        HtmlAnchor element = getAnchor(page.getDocumentElement(), tag);

        if (element == null) {
            List<HtmlPage> innerPages = getInnerPages(page);
            for (HtmlPage innerPage : innerPages) {
                element = getAnchor(innerPage, tag);
                if (element != null) {
                    break;
                }
            }
        }

        return element;
    }

    /**
     * Gets an Anchor (hyperlink) element whose HREF attribute string contains
     * the given tag string.
     *
     * @param element the HTML element to look in.
     * @param tag the string to look for in HREF.
     * @return the HTML Anchor element or null if not found.
     */
    private HtmlAnchor getAnchor(HtmlElement element, String tag) {
        for (HtmlElement e : element.getHtmlElementDescendants()) {
            if (e instanceof HtmlAnchor) {
                String href = e.getAttribute("href");
                if (href != null && href.contains(tag)) {
                    return (HtmlAnchor) e;
                }
            }
        }
        return null;
    }

    /**
     * Finds all of the Help HTML elements (hyperlinks) in a web page.
     *
     * HTML web pages may contain Inline Frames (iframes) which are not expanded
     * within HtmlUnit.  The inline frames contain inner web pages that must
     * also be searched.
     *
     * @param page the HTML web page.
     * @return the list of Help HTML elements.
     */
    protected final List<HtmlAnchor> findHelpLinks(HtmlPage page) {
        List <HtmlAnchor> helpLinks = findHelpLinks(page.getDocumentElement());

        List<HtmlPage> innerPages = getInnerPages(page);
        for (HtmlPage innerPage : innerPages) {
            helpLinks.addAll(findHelpLinks(innerPage));
        }

        return helpLinks;
    }

    /**
     * Finds all of the Help HTML elements (hyperlinks) in a parent HTML element.
     * In Kuali, all Help hyperlinks have a target window with value of "helpWindow".
     *
     * @param element the parent HTML element.
     * @return the list of Help HTML elements.
     */
    private List<HtmlAnchor> findHelpLinks(HtmlElement element) {
        List<HtmlAnchor> helpLinks = new ArrayList<HtmlAnchor>();
        for (HtmlElement e : element.getHtmlElementDescendants()) {
            if (e instanceof HtmlAnchor) {
                String target = e.getAttribute("target");
                if (target != null && target.equals("helpWindow")) {
                    helpLinks.add((HtmlAnchor) e);
                }
            }
        }
        return helpLinks;
    }

    /**
     * Gets an HTML element in the web page.  Searches the web page for
     * the first HTML element whose name attribute matches the given name.
     *
     * HTML web pages may contain Inline Frames (iframes) which are not expanded
     * within HtmlUnit.  The inline frames contain inner web pages that must
     * also be searched.
     *
     * @param page the HTML web page to search.
     * @param name the name to search for.
     * @param startsWith if true, only match against the start of the name.
     * @return the HTML element or null if not found.
     */
    protected final List<HtmlElement> getAllElementsByName(HtmlPage page, String name, boolean startsWith) {
        List<HtmlElement> elements = getAllElementsByName(page.getDocumentElement(), name, startsWith);

        List<HtmlPage> innerPages = getInnerPages(page);
        for (HtmlPage innerPage : innerPages) {
            elements.addAll(getAllElementsByName(innerPage, name, startsWith));
        }

        return elements;
    }

    /**
     * Gets an HTML element in a parent HTML element.  Searches the parent HTML
     * element for the first HTML element whose name attribute matches the given name.
     *
     * @param element the parent HTML element to search.
     * @param name the name to search for.
     * @param startsWith if true, only match against the start of the name.
     * @return the HTML element or null if not found.
     */
    protected final List<HtmlElement> getAllElementsByName(HtmlElement element, String name, boolean startsWith) {
        List<HtmlElement> elements = new ArrayList<HtmlElement>();
        for (HtmlElement e : element.getHtmlElementDescendants()) {
            String value = e.getAttribute("name");
            if (!startsWith && name.equals(value)) {
                elements.add(e);
            } else if (startsWith && value != null && value.startsWith(name)) {
                elements.add(e);
            }
        }
        return elements;
    }

    /**
     * Gets the list of inner web pages.  Inner web pages are contained
     * within Inline Frames (iframes).
     *
     * @param page the HTML web page to search for inner web pages.
     * @return the list of inner HTML web pages.
     */
    protected final List<HtmlPage> getInnerPages(HtmlPage page) {
        List<HtmlPage> innerPages = new ArrayList<HtmlPage>();

        List<FrameWindow> frames = page.getFrames();
        for (int i = 0; i < frames.size(); i++) {
            FrameWindow frame = frames.get(i);
            BaseFrame baseFrame = frame.getFrameElement();
            if (baseFrame instanceof HtmlInlineFrame) {
                HtmlInlineFrame iframe = (HtmlInlineFrame) baseFrame;
                Page epage = iframe.getEnclosedPage();
                if (epage instanceof HtmlPage) {
                    innerPages.add((HtmlPage) epage);
                }
            }
        }
        return innerPages;
    }

    /**
     * Get the first HTML child element.
     *
     * @param element the parent HTML element.
     * @return the first child HTML element or null if there are no children.
     */
    protected final HtmlElement getFirstChild(HtmlElement element) {
        HtmlElement firstChild = null;

        Iterator<HtmlElement> iterator = element.getChildElements().iterator();
        if (iterator.hasNext()) {
            firstChild = iterator.next();
        }
        return firstChild;
    }

    /**
     * Get the next sibling HTML element.
     *
     * @param element an HTML element.
     * @return the next sibling HTML element or null if there is none.
     */
    protected final HtmlElement getNextSibling(HtmlElement element) {
        HtmlElement sibling = null;
        DomNode node = element.getParentNode();
        if (node instanceof HtmlElement) {
            HtmlElement parent = (HtmlElement) node;

            Iterator<HtmlElement> iterator = parent.getChildElements().iterator();
            while (iterator.hasNext()) {
                HtmlElement e = iterator.next();
                if (e == element) {
                    if (iterator.hasNext()) {
                        sibling = iterator.next();
                    }
                    break;
                }
            }
        }
        return sibling;
    }
    
    /**
     * Get the next sibling HTML element.
     *
     * @param element an HTML element.
     * @return the next sibling HTML element or null if there is none.
     */
    protected final HtmlElement getNextSibling(HtmlElement element, String elementName) {
        DomNode node = element.getParentNode();
        if (node instanceof HtmlElement) {
            HtmlElement parent = (HtmlElement) node;

            Iterator<HtmlElement> iterator = parent.getChildElements().iterator();
            while (iterator.hasNext()) {
                HtmlElement e = iterator.next();
                if (e == element) {
                    while (iterator.hasNext()) {
                        HtmlElement sibling = iterator.next();
                        if (StringUtils.equalsIgnoreCase(sibling.getNodeName(), elementName)) {
                            return sibling;
                        }
                    }
                    break;
                }
            }
        }
        return null;
    }

    /**
     * Do a document search looking for the a specific document based upon its docNbr.
     * The search begins at the Portal Page and then navigates to the Doc Search Page.
     * The given docNbr is added to the search field and search is performed.  If the
     * doc is found, it's hyperlink is clicked and the resulting page is returned.
     *
     * @param docNbr the docNbr to search for
     * @return the document's page or null if not found.
     * @throws IOException
     */
    protected final HtmlPage docSearch(String docNbr) throws IOException {
        HtmlPage portal = getPortalPage();
        HtmlPage docSearchPage = clickOn(portal, "Document Search");

        setFieldValue(docSearchPage, "routeHeaderId", docNbr);
        docSearchPage = clickOn(docSearchPage, "methodToCall.search");
        if (docSearchPage.asText().contains("Nothing found to display.")) {
            //do nothing
        } else if (docSearchPage.asText().contains("No values match this search")) {
            //do nothing
        } else {
            HtmlAnchor hyperlink = getAnchor(docSearchPage, "docId="+docNbr);
            assertNotNull(hyperlink);
            return clickOn(hyperlink);
        }
        return null;
    }

    /**
     * Gets the docNbr from a document's web page.
     * The docNbr is expected to be in an HTML table
     * is the division labeled "headerarea".
     *
     * @param page the documen's web page
     * @return the document's docNbr
     */
    protected final String getDocNbr(HtmlPage page) {
        HtmlTable table = getTable(page, "headerarea");
        HtmlTableRow row = table.getRow(0);
        HtmlTableCell cell = row.getCell(1);
        return cell.asText().trim();
    }

    /**
     * Save a document, i.e. click on Save button.
     *
     * @param page the document web page
     * @return the next page
     * @throws IOException
     */
    protected final HtmlPage saveDoc(HtmlPage page) throws IOException {
        return clickOn(page, "save");
    }

    /**
     * Closes a document.  If queried to save the document, the
     * "No" button is clicked on.
     *
     * @param page the document web page
     * @return the next page
     * @throws IOException
     */
    protected final HtmlPage closeDoc(HtmlPage page) throws IOException {
        HtmlPage nextPage = clickOn(page, "close");
        if (nextPage.asText().contains("Would you like to save this document before you close it")) {
            nextPage = clickOn(nextPage, "methodToCall.processAnswer.button1");
        }
        return nextPage;
    }

    /**
     * Saves and closes a document.  After saving and closing the document,
     * the document's number is returned.  It would have been logical to
     * return the next web page, but in this testing environment, it is
     * anticipated that the document number will be used to perform a document
     * search.
     *
     * @param page the document web page
     * @return the document's number
     * @throws IOException
     */
    protected final String saveAndCloseDoc(HtmlPage page) throws IOException {
        String docNbr = getDocNbr(page);
        HtmlPage savedPage = saveDoc(page);
        closeDoc(savedPage);
        return docNbr;
    }

    /**
     * Saves a document and then performs a document search to retrieve
     * the document.  This is useful for verifying that a document can be
     * saved correctly.  After retrieving a saved document, its values can
     * be inspected to verify their correctness.
     *
     * @param docPage the web page containing the document.
     * @return the retrieved document web page.
     * @throws Exception
     */
    protected final HtmlPage saveAndSearchDoc(HtmlPage docPage) throws Exception {
        String docNbr = saveAndCloseDoc(docPage);
        HtmlPage newPage = docSearch(docNbr);
        assertNotNull(newPage);
        return newPage;
    }

    /**
     * Gets the list of options in a select field.  This list only
     * contains the text that is displayed to the user, not the
     * actual values sent to the web server in a POST.
     *
     * @param page the web page.
     * @param id the id of the select field.
     * @return the list of displayed options.
     */
    protected final List<String> getSelectOptions(HtmlPage page, String id) {
        List<String> options = new ArrayList<String>();

        HtmlElement element = getElement(page, id);
        assertNotNull(element);
        assertTrue(element.getClass().getName(), element instanceof HtmlSelect);

        HtmlSelect select = (HtmlSelect) element;
        for (int i = 0; i < select.getOptionSize(); i++) {
            HtmlOption option = select.getOption(i);
            options.add(option.asText().trim());
        }

        return options;
    }

    /**
     * Verify that all the Help links on the web page go to the Kuali Help Web Page.
     * This will also test the help links on other panels on the page, but no big deal. This is not enabled in
     *  tests by default. If you want your test to do this, you need to add the <code>@Test</code> annotation
     *  and override it in your test class.
     * @throws Exception
     */
    public void testHelpLinks() throws Exception {
        checkHelpLinks(getDefaultDocumentPage());
    }

    /**
     * Used by generic methods like <code>{@link #testHelpLinks()}</code> for general behavior that is particular to a
     * page, but we don't know what that page is. This should be <code>abstract</code>, but that would break stuff.
     *
     * @return <code>{@link HtmlPage}</code> instance of a page that is used frequently in this test class
     */
    protected HtmlPage getDefaultDocumentPage() throws Exception {
        return null;
    }

    /**
     * Sets a select field to any of options except for "select:".
     * This is useful if you don't know the possible options in a
     * select field and you don't care which one is selected.
     *
     * @param page the HTML web page.
     * @param id the id of the HTML select tag.
     */
    protected void selectAnyOption(HtmlPage page, String id) {
        HtmlElement element = getElement(page, id);
        assertTrue(element.getClass().getName(), element instanceof HtmlSelect);

        HtmlSelect selectField = (HtmlSelect) element;
        List<HtmlOption> options = selectField.getOptions();
        for (HtmlOption option : options) {
            String value = option.getValueAttribute();
            if (!value.equals("")) {
                selectField.setSelectedAttribute(value, true);
                break;
            }
        }
    }
    /**
     * 
     * Sets a select field to the value of the option specified by the optionOrdinalPosition
     * @param page
     * @param id
     * @param optionOrdinalPosition
     */
    protected void selectSpecificOption(HtmlPage page, String id, int optionOrdinalPosition) {
        HtmlElement element = getElement(page, id);
        assertTrue(element instanceof HtmlSelect);

        HtmlSelect selectField = (HtmlSelect) element;
        List<HtmlOption> options = selectField.getOptions();
        selectField.setSelectedAttribute(options.get(optionOrdinalPosition).getValueAttribute(), true);
    }
    
    private String createExpectedFieldNotFoundMessage(HtmlPage page, String fieldId) {
        return String.format("Expected field %s was not found on page titled %s", fieldId, page.getTitleText());
    }

    /**
     * This method is a weak check for error text falling in the proper location. 
     * It just checks that the error text falls after a div with the specified id and 
     * also after the "Errors found in this Section:" message. However, since no check is done 
     * using the HTML DOM, it's not guaranteed that the error does appear inside the tab
     *  
     * @param page
     * @param expectedErrorText This is the error message text expected to be displayed
     * @param containingElementId This would typically be the generated id for the tab
     */
    protected void assertErrorTextContainedWithinSection(HtmlPage page, String expectedErrorText, String containingElementId) {
        final String xml = page.asXml();
        final int textPosition = xml.indexOf(expectedErrorText);
        assertTrue("Text not found on page: " + expectedErrorText, textPosition > -1);
        final int idPosition = xml.indexOf(containingElementId);
        assertTrue("Containing id not found on page: " + containingElementId, idPosition > -1);
        final int sectionErrorMessagePosition = xml.indexOf(SECTION_ERROR_LABEL, idPosition);
        assertTrue("Errors not found in section. Please check tabErrorKey.", sectionErrorMessagePosition > -1);
        // This next check is in case there are other tabs in error. We just check if the error message comes after 
        // the element with specified ID and the section error message. It's a weak check and probably redundant for most tests
        assertTrue("Section errors not found after containingElementId", (sectionErrorMessagePosition > idPosition && textPosition > sectionErrorMessagePosition));
        
    }
}
