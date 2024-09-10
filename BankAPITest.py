import requests
import json

def makeUser(id, name, savVal, checkVal):
    return {
        "id": id,
        "name": name,
        "savings": {
            "balance": savVal,
            "accountType": "savings"
        },
        "checking": {
            "balance": checkVal,
            "accountType": "checking"
        }
    }

def cleanPrint(response, description):
    try:
        json_data = response.json()
        print(f"{description} (Status: {response.status_code}):\n{json.dumps(json_data, indent=4)}")
    except ValueError:  # If the response body isn't valid JSON
        print(f"{description} (Status: {response.status_code}):\n{response.text}")

def sendCreateUser(user):
    json_data = json.dumps(user)
    response = requests.post("http://localhost:8080/createUser", headers={"Content-Type": "application/json"}, data=json_data)
    cleanPrint(response, "Create User")

def sendTransferFunds(id, funds, direction):
    transfer = {
        "id": id,
        "funds": funds,
        "direction": direction
    }
    json_data = json.dumps(transfer)
    response = requests.post("http://localhost:8080/transferFunds", headers={"Content-Type": "application/json"}, data=json_data)
    cleanPrint(response, "Transfer Funds")

def sendGetAccount(id):
    response = requests.get("http://localhost:8080/getUser", params={"id": id})
    cleanPrint(response, "Get Account")
    return response.text

def sendGetAllUsers():
    response = requests.get("http://localhost:8080/getAllUsers")
    cleanPrint(response, "Get All Users")

def sendGetHistory(id):
    response = requests.get("http://localhost:8080/getHistory", params={"id": id})
    cleanPrint(response, "Get History")


# Testing the methods with cleaner output
print("Creating Ann Elk")
userAnn = makeUser("123", "Ann Elk", 1000, 500)
sendCreateUser(userAnn)

print("\nCreating On First")
userOn = makeUser("124", "On First", 20, 2000)
sendCreateUser(userOn)

print("\nCreating user with no ID")
user = makeUser("", "Name here", 500, 500)
sendCreateUser(user)

print("\nCreating user with no name")
user = makeUser("125", "", 500, 500)
sendCreateUser(user)

print("\nCreating user with invalid account balance")
user = makeUser("126", "User Name", -10, 500)
sendCreateUser(user)

print("\nAnn's account: ")
sendGetAccount("123")

print("\nAnn's account with invalid id: ")
sendGetAccount("1")

print("\nTransfer $100 to Ann Elk's savings from checking")
sendTransferFunds("123", 100, "savings")

print("\nTransfer $300 to Ann Elk's checking from savings")
sendTransferFunds("123", 300, "checking")

print("\nTransfer $1000000 to Ann Elk's savings from checking")
sendTransferFunds("123", 1000000, "savings")

print("\nTransfer -$10 to Ann Elk's checking from savings")
sendTransferFunds("123", -10, "checking")

print("\nGetting all users")
sendGetAllUsers()

print("\nGetting Ann Elk's history")
sendGetHistory("123")
