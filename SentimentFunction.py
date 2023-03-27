import pandas as pd
import numpy as np
from urllib.request import urlopen, Request
from bs4 import BeautifulSoup
import os
import matplotlib.pyplot as plt
# %matplotlib inline
# For Sentiment Analysis
import nltk
nltk.downloader.download('vader_lexicon')
from nltk.sentiment.vader import SentimentIntensityAnalyzer


from flask import Flask


def create_app():
    app = Flask(__name__)
    @app.route('/')
    def hello_world():
        return 'Hello, World!'
    @app.route('/sentiment/<ticker_name>')
    def sentiment_result(ticker_name):
        a = sentiment(ticker_name)
        if a >= 0.05:
            return("positive")
        elif a<=-0.05:
            return("negative")
        else:
            return("neutral")

    return app
app = create_app()




def sentiment(str):
    n = 3
    # stock = input("Enter the stock: ")
    # print(stock)
    str = str.upper()
    # print(stock)
    tickers=[]
    tickers.append(str)
    finwiz_url = 'https://finviz.com/quote.ashx?t='
    news_tables = {}
    for ticker in tickers:
        url = finwiz_url + ticker
        req = Request(url=url,headers={'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0'}) 
        result = urlopen(req)    
        # Place the contents of the file in html page named 'html'
        html = BeautifulSoup(result, features="lxml")
        news_table = html.find(id='news-table')
        news_tables[ticker] = news_table

    try:
        for ticker in tickers:
            df = news_tables[ticker]
            df_tr = df.findAll('tr')
            for i, table_row in enumerate(df_tr):
            # Read the text of the element 'a' 
                a_text = table_row.a.text
            # Read the text of the element 'td' 
                td_text = table_row.td.text
            #Clean data using strip()
                td_text = td_text.strip()
            #   print(a_text)
            #   print(td_text)

            if i == n-1:
                break
    except KeyError:
        pass


    parsed_news = []
    for file_name, news_table in news_tables.items():
        try:
            for x in news_table.findAll('tr'):
                text = x.a.get_text()
                date_scrape = x.td.text.split()
            
                if len(date_scrape) == 1:
                    time = date_scrape[0]
                
            # else load 'date' as the 1st element and 'time' as the second    
                else:
                    date = date_scrape[0]
                    time = date_scrape[1]
            # Extract the ticker from the file name, get the string up to the 1st '_'  
                ticker = file_name.split('_')[0]
            
                parsed_news.append([ticker, date, time, text])
        
        except KeyError:
            pass    
    # parsed_news



    vader_analysis = SentimentIntensityAnalyzer()
    columns = ['Ticker', 'Date', 'Time', 'Headline']

    parsed_and_scored_news = pd.DataFrame(parsed_news, columns=columns)

    scores = parsed_and_scored_news['Headline'].apply(vader_analysis.polarity_scores).tolist()

    scores_df = pd.DataFrame(scores)

    parsed_and_scored_news = parsed_and_scored_news.join(scores_df, rsuffix='_right')

    parsed_and_scored_news['Date'] = pd.to_datetime(parsed_and_scored_news.Date).dt.date


    parsed_and_scored_news = parsed_and_scored_news.set_index('Ticker')

    # parsed_and_scored_news



    df = parsed_and_scored_news
    df = df.drop(columns = ['Headline','Date','Time', 'neg', 'pos', 'neu'])
    mean = round(df.mean(), 4)
    # print(df)
    return mean.compound
    # print("Final Sentiment: ", mean)
    # a positive sentiment, compound ≥ 0.05
    # a negative sentiment, compound ≤ -0.05
    # a neutral sentiment, the compound is between [-0.05, 0.05]


# stock_name = input("Enter the stock: ")
# final_result = sentiment(stock_name)
# print(final_result)


