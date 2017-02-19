import numpy
import matplotlib.pyplot as plt
import pandas
import math
from keras.models import Sequential
from keras.layers import Dense
from keras.layers import LSTM
from sklearn.preprocessing import MinMaxScaler
from sklearn.metrics import mean_squared_error
# convert an array of values into a dataset matrix
def create_dataset(dataset, look_back):
	dataX, dataY = [], []
	for i in range(len(dataset)-look_back-1):
		a = dataset[i:(i+look_back), 0]
		dataX.append(a)
		dataY.append(dataset[i + look_back])
	return numpy.array(dataX), numpy.array(dataY)
# fix random seed for reproducibility
numpy.random.seed(7)
# load the dataset
dataframe = pandas.read_csv('percentage.csv', usecols=[0,1,2,3], engine='python' )
dataset = dataframe.values
dataset = dataset.astype('float32')
train = dataset
# reshape into X=t and Y=t+1
look_back = 4
trainX, trainY = create_dataset(train, look_back)
#testX, testY = create_dataset(test, look_back)
# reshape input to be [samples, time steps, features]
trainX = numpy.reshape(trainX, (trainX.shape[0], 1, trainX.shape[1]))
#testX = numpy.reshape(testX, (testX.shape[0], 1, testX.shape[1]))
# create and fit the LSTM network
model = Sequential()
model.add(LSTM(32, input_dim=4, input_length=1))
model.add(Dense(4))
model.compile(loss='mean_squared_error', optimizer='adam')
model.fit(trainX, trainY, nb_epoch=50, batch_size=8)
score = model.evaluate(trainX,trainY,batch_size=16)
print('\n',score*100)
model_json = model.to_json()
with open("model.json", "w") as json_file:
    json_file.write(model_json)
model.save_weights("model.h5")
# make predictions
trainPredict = model.predict(trainX)
#testPredict = model.predict(testX)

numpy.save("predictions", trainPredict)
print(trainPredict)
#print(testPredict)
print('\n')
testX = numpy.reshape(trainPredict, (trainPredict.shape[0], 1, trainPredict.shape[1]))
print (testX[len(testX)-2])
print('\n')
testPredict = model.predict(testX[len(testX)-2:len(testX)-1])
print(testPredict)