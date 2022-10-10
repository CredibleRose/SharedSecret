const Ajax = require("./async");
const axios = require("axios");

// class fakeServer {
//     constructor(data) {
//         this.data = data;
//     }
//     res = {
//         listForSecrets: () => {
//             return Promise.resolve(
//             [{
//                     uuid: 1,
//                     encryptedSecret: "Some text for test",
//                     password: "",
//                     needPassword: false,
//                     timeToBurn: "",
//                     showAndBurn: false
//                 },
//                 {
//                     uuid: 2,
//                     encryptedSecret: "Test text",
//                     password: "password",
//                     needPassword: true,
//                     timeToBurn: "",
//                     showAndBurn: false
//                 },
//                 {
//                     uuid: 3,
//                     encryptedSecret: "Text",
//                     password: "password",
//                     needPassword: true,
//                     timeToBurn: "111444",
//                     showAndBurn: false
//                 },
//                 {
//                     uuid: 4
//                 }
//             ]
//             );
//         }
//     }
// }

// const getDataFromClient = async (uuid) => {
//     const client = new fakeServer();
//     const data = await client.res.listForSecrets(uuid);
//     return data.uuid;
// }
//
// const result = getDataFromClient(1);
// console.log(result);
// jest.mock("axios");
// axios
//     .get
//     .mockResolvedValue(
//         [{
//             uuid: "1",
//             encryptedSecret: "Some text for test",
//             password: "",
//             needPassword: false,
//             timeToBurn: "",
//             showAndBurn: false
//         },
//             {
//                 uuid: 2,
//                 encryptedSecret: "Test text",
//                 password: "password",
//                 needPassword: true,
//                 timeToBurn: "",
//                 showAndBurn: false
//             },
//             {
//                 uuid: 3,
//                 encryptedSecret: "Text",
//                 password: "password",
//                 needPassword: true,
//                 timeToBurn: "111444",
//                 showAndBurn: false
//             },
//             {
//                 uuid: 4
//             }
//         ]
//     )


describe("GET data from API", () => {
    test("Should return correct data from server", async () => {
        const result = await Ajax.getData("1e929d47-dfc2-4f9b-a5c2-f37108047487");
        expect(result).toBe("U2FsdGVkX1+c8B6nGh6/Gjh8eMOB/tOJuA0VcULX2j0=");
    });
    test("Should not find data", async () => {
        const result = await Ajax.getData("NotRealUuid");
        expect(result).toBe(undefined);
    });
});

describe("Correct operation of AES encryption", () => {
    test("Should encrypt and decrypt the original text", () => {
    });
});

describe("Correct generate link", () => {
    test("Should create correct link", () => {
        const generatedLink = Ajax.generateLink("1234", "HashKey");
        expect(generatedLink).toBe("localhost:8080/answer/1234#HashKey");
    });
})
