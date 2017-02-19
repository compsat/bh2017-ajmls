import numpy
import matplotlib.pyplot as plt
import pandas
import math
from keras.models import Sequential
from keras.layers import Dense
from keras.layers import LSTM
from keras.models import model_from_json
from sklearn.preprocessing import MinMaxScaler
from sklearn.metrics import mean_squared_error
import sys

year = int(sys.argv[1])
lengthOfSlider = int(sys.argv[2])

predictions = numpy.load("assets/predictions.npy" )
toPredict = numpy.reshape(predictions, (predictions.shape[0], 1, predictions.shape[1]))
json_file = open('assets/model.json', 'r')
loaded_model_json = json_file.read()
json_file.close()
loaded_model = model_from_json(loaded_model_json)
# load weights into new model
loaded_model.load_weights("assets/model.h5")
baseNum = year-1977
newRange = baseNum+lengthOfSlider
i=0
if baseNum+lengthOfSlider < len(predictions):
	while i < lengthOfSlider:
		print(predictions[baseNum+i])
		i=i+1
else:
	while len(predictions) < newRange:
		if baseNum+i < len(predictions):
			print(predictions[baseNum+i])
			i=i+1
		else:
			newPrediction = loaded_model.predict(toPredict[len(predictions)-2:len(predictions)-1])
			if len(predictions) >=baseNum:
				print(newPrediction)
			predictions= numpy.vstack((predictions, newPrediction[0]))
			toPredict = numpy.reshape(predictions, (predictions.shape[0], 1, predictions.shape[1]))
numpy.save("assets/predictions",predictions)