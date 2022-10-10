const axios = require('axios');

class Ajax {
    static echo(data) {
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                if (data) {
                    resolve(data);
                } else {
                    reject(Error('Error'));
                }
            }, 150);
        });
    }

    static async getData(uuid) {
        try {
            const response = await axios.get(`http://localhost:8080/api/secret/?uuid=${uuid}`);
            console.log(response.data.encryptedSecret)
            return response.data.encryptedSecret;
        } catch (err) {
            console.log("Error");
        }
    }

    static generateLink(uuid, key) {
        return "localhost:8080/" + "answer/" + uuid + "#" + key;
    }
}

module.exports = Ajax;