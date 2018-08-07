Data of S&P500 is used.

There are three modules:
	1>Technical Analyzer
	2>Sentiment Analyzer
	3>ML_Predictor


Technical Analyzer: 
OHLC is used to construct technical indicators which are then fed to the predictor. These indicators include SMA(Simple Moving Average),Candlestick patterns, MACD(Moving Average Convergence Divergence), RSI(Relative Strength Index). They are being coded in Java.

Sentiment Analyzer:
Scrapes data from twitter and create bag of words. Assigns +1 for positive, -1 for negative and 0 for neutral sentiment. The module is written in Python.

ML_Predictor:
It is an ensemble of 3 models: KNN, SVM and RFC. Features generated from technical analyzer and sentiment analyzer are forwarded to this module for making predictions.
