from flask import Flask, jsonify
from json import load

app = Flask(__name__)


@app.route('/theme')
def samplefunction():
    with open('theme.json') as f:
        data = load(f)
    return jsonify(data)


if __name__ == '__main__':
    port = 8000 #the custom port you want
    app.run(host='0.0.0.0', port=port)

