from flask import Flask


f = open('y_predicted.json')


def create_app():
    app = Flask(__name__)
    @app.route('/')
    def hello_worlds():
        return 'Hello, World This is Prediction!'
    
    @app.route('/prediction')
    def prediction_result():
        return f
        
    return app
app = create_app()