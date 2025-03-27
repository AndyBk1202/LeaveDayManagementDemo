## API Specification

### Authentication APIs

#### 1. Register user
- **URL**: `POST /auth/register`
- **Description**: Đăng kí tài khoản người dùng.
- **Request Body**:
  ```json
  {
     "name": "Nguyen Cong Thanh",
     "email": "johndoe10@example.com",
     "password": "securepassword"
  }
  ```
- **Response**:
    - `200 OK`: Register successfully.
      ```json
      {
          "status": 200,
          "message": "Register successfully"
      }
      ```
    - `400 User existed`
      ```json
      {
         "status": 400,
         "message": "This username already existed, please choose another username"     
      }
      ```

#### 2. Login user
- **URL**: `POST /auth/login`
- **Description**: Đăng nhập tài khoản người dùng.
- **Request Body**:
  ```json
  {
     "email": "johndoe10@example.com",
     "password": "securepassword"
  }
  ```
- **Response**:
    - `200 OK`: Login successfully.
      ```json
      {
          "status": 200,
          "message": "Register successfully"
      }
      ```
    - `401 Bad Credentials`
      ```json
      {
         "status": 401,
         "message": "Invalid username or password"     
      }
      ```
    - `404 User not found`
      ```json
      {
         "status": 404,
         "message": "Username not found"     
      }
      ```
  - `500 Exceptional error`
    ```json
    {
       "status": 500,
       "message": "Error Occurs During User Login: "     
    }
    ```
    
### Users APIs

#### 1. Update user
- **URL**: `PUT /users/update`
- **Description**: Cập nhật người dùng.
- **Request Body**:
  ```json
  {
     "email": "Nguyen Thi Anh Dao",
     "email": "johndoe10@example.com",
     "password": "securepassword"
  }
  ```
  - **Response**:
      - `200 OK`: Added successfully.
        ```json
        {
            "status": 200,
            "message": "Update user successfully",
            "user": {
                "id": 1,
                "name": "Nguyen Cong Thanh",
                "email": "johndoe10@example.com",
                "password": "securepassword",
                "role": "EMPLOYEE"
            }
        }
        ```
      - `404 Users existed`
        ```json
        {
           "status": 404,
           "message": "User not found"     
        }
        ```

#### 2. Delete users
- **URL**: `DELETE users/delete/{id}`
- **Description**: Xóa người dùng.
- **Path variable**: long id
- **Response**:
    - `200 OK`: Deleted successfully.
      ```json
      {
          "status": 200,
          "message": "User deleted successfully"
      }
      ```
    - `404 User not found`
        ```json
          {
            "statusCode": 404,
            "message": "Device not found"     
          }
        ```

#### 3. View 1 User
- **URL**: `GET users/view/{id}`
- **Description**: Xem 1 người dùng.
- **Path variable**: long id
  - **Response**:
      - `200 OK`: View successfully.
        ```json
        {
        "status": 200,
        "message": "User found",
        "userDTO": {
                "id": 1,
                "name": "Andy",
                "email": "johndoe10@example.com"
            }
        }
        ```
      - `404 User not found`
          ```json
            {
              "statusCode": 404,
              "message": "User not found"     
            }
          ```

#### 4. View All User
- **URL**: `GET users/view`
  - **Description**: Xem tất cả người dùng.
      - **Response**:
          - `200 OK`: View successfully.
            ```json
            {
                "status": 200,
                "message": "User found",
                "userDTOList": [
                    {
                        "id": 1,
                        "name": "Nguyen Cong Thanh",
                        "email": "johndoe10@example.com",
                        "password": null
                    },
                    {
                        "id": 2,
                        "name": "Andy",
                        "email": "johndoe20@example.com",
                        "password": null
                    },
                    {
                        "id": 3,
                        "name": "Nguyen Cong Thanh",
                        "email": "johndoe30@example.com",
                        "password": null
                    }
                ]
            }
            ```
