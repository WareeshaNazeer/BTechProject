import bs4 as bs     #web data scraper
import datetime as dt
import numpy as np
from sklearn import svm, cross_validation, neighbors
from sklearn.neural_network import MLPClassifier
from sklearn.preprocessing import StandardScaler  
from sklearn.ensemble import VotingClassifier, RandomForestClassifier
from sklearn.ensemble import AdaBoostClassifier
from collections import Counter
import os
import pandas as pd
import pandas_datareader.data as web
import fix_yahoo_finance 
import pickle        #serializes any object
import requests


def process_data_for_labels(ticker):
    hm_days = 7
    df = pd.read_csv('sp500_joined_closes.csv', index_col=0)
    tickers = df.columns.values.tolist()
    df.fillna(0, inplace=True)
    
    for i in range(1,hm_days+1):
        df['{}_{}d'.format(ticker,i)] = (df[ticker].shift(-i) - df[ticker]) / df[ticker]
        
    df.fillna(0, inplace=True)
    #print(df.head())
    return tickers, df


def buy_sell_hold(*args):
    cols = [c for c in args]
    requirement = 0.02
    for col in cols:
        if col > requirement:
            return 1		#buy
        if col < -requirement:
            return -1		#sell
    return 0			#hold

def extract_featuresets(ticker):
    tickers, df = process_data_for_labels(ticker)

    df['{}_target'.format(ticker)] = list(map( buy_sell_hold,
                                               df['{}_1d'.format(ticker)],
                                               df['{}_2d'.format(ticker)],
                                               df['{}_3d'.format(ticker)],
                                               df['{}_4d'.format(ticker)],
                                               df['{}_5d'.format(ticker)],
                                               df['{}_6d'.format(ticker)],
                                               df['{}_7d'.format(ticker)] ))

    print(df.head())

#extract_featuresets("AES")
    vals = df['{}_target'.format(ticker)].values.tolist()
    str_vals = [str(i) for i in vals]
    print('Data spread:',Counter(str_vals))

    df.fillna(0, inplace=True)
    df = df.replace([np.inf, -np.inf], np.nan)
    df.dropna(inplace=True)

    df_vals = df[[ticker for ticker in tickers]].pct_change()
    df_vals = df_vals.replace([np.inf, -np.inf], 0)
    df_vals.fillna(0, inplace=True)

    X = df_vals.values
    y = df['{}_target'.format(ticker)].values

    #print(X,y)
    
    return X,y,df

extract_featuresets('AEP')

def do_ml(ticker):
    X, y, df = extract_featuresets(ticker)
    X_train, X_test, y_train, y_test = cross_validation.train_test_split(X,
                                                        y,
                                                        test_size=0.25)
    
    scaler = StandardScaler()  
    # Don't cheat - fit only on training data
    scaler.fit(X_train)  
    X_train = scaler.transform(X_train)  
    # apply same transformation to test data
    X_test = scaler.transform(X_test)  

    #clf = MLPClassifier(hidden_layer_sizes=(15,), random_state=1, max_iter=1, warm_start=True)


    #clf = neighbors.KNeighborsClassifier()
    #clf = RandomForestClassifier()
    clf = VotingClassifier([('lsvc',svm.LinearSVC()),
                           ('knn',neighbors.KNeighborsClassifier()),
                           ('rfor',RandomForestClassifier())])
    #clf = AdaBoostClassifier()
   # clf=RandomForestClassifier()
    clf.fit(X_train, y_train)

    confidence = clf.score(X_test, y_test)
    print('accuracy:',confidence)
    predictions = clf.predict(X_test)
    print('predicted class counts:',Counter(predictions))


do_ml('AEP')
