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
    
### Users APIs

#### 1. Get My Info
- **URL**: `GET /users/my-info`
- **Description**: Lấy thông tin người đang đăng nhập
- **Response**:
    - `200 OK`: Added successfully.
      ```json
      {
          "status": 200,
          "message": "My info fetched successfully",
          "myInfoDTO": {
              "id": 5,
              "name": "NCT",
              "email": "nguyencongthanh2406@gmail.com",
              "role": "EMPLOYEE"
          }
      }
      ```
    - `404 Users not found`
      ```json
      {
         "status": 404,
         "message": "User not found"     
      }
      ```

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
    - `404 Users not found`
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

### Leave Requests APIs

#### 1. Send leave requests
- **URL**: `POST /leave-requests/send`
- **Description**: Gửi yêu cầu nghỉ.
- **Request Body**:
  ```json
  {     
      "startDate": "2025-03-29",       
      "endDate": "2025-03-30",       
      "reason": "Mệt mỏi"
  }
  ```
- **Response**:
    - `200 OK`: Send successfully.
      ```json
      {
          "status": 200,
          "message": "Leave request submitted successfully"
      }
      ```
    - `400 Invalid resource`
      ```json
      {
         "status": 400,
         "message": "Start date cannot be after end date"     
      }
      ```
  - `400 User not found`
    ```json
    {
       "status": 400,
       "message": "User not found"     
    }
    ```
  - `400 Invalid resource`
    ```json
    {
       "status": 400,
       "message": "Token is expired"     
    }
    ```
  - `400 User not found`
    ```json
    {
       "status": 400,
       "message": "Something went wrong in JWT extraction"     
    }
    ```
  - `500 Exceptional error`
    ```json
    {
       "status": 500,
       "message": "Error submitting leave request: "     
    }
    ```

#### 2. Update leave requests
- **URL**: `PUT /leave-requests/update/{id}`
- **Description**: Cập nhật yêu cầu nghỉ.
- **Request Body**:
  ```json
  {     
      "startDate": "2025-03-29",       
      "endDate": "2025-03-30",       
      "reason": "Mệt mỏi",
      "status": "PENDING"
  }
  ```
- **Response**:
    - `200 OK`: Send successfully.
      ```json
      {
          "status": 200,
          "message": "Leave request updated successfully"
      }
      ```
    - `400 Status already set ACCEPTED`
      ```json
      {
         "status": 400,
         "message": "Leave request has already been accepted and cannot be updated"     
      }
      ```
    - `400 Status already set REJECTED`
      ```json
      {
         "status": 400,
         "message": "Leave request has already been rejected and cannot be updated"     
      }
      ```
    - `400 Leave requests not found`
      ```json
      {
         "status": 400,
         "message": "Leave Request Not Found"     
      }
      ```
    - `500 Exceptional error`
      ```json
      {
         "status": 500,
         "message": "Error updating leave request: "     
      }
      ```

#### 3. View Leave Requests By Id
- **URL**: `GET leave-requests/view/{id}`
- **Description**: Xem yêu cầu xin nghỉ theo id.
- **Path variable**: long id
- **Response**:
    - `200 OK`: View successfully.
  ```json
    {
      "status": 200,
      "message": "Fetching all leave requests successfully",
      "leaveRequestDTOList": [
          {
              "id": 4,
              "startDate": "2025-04-01",
              "endDate": "2025-04-10",
              "reason": "Nghỉ phép cá nhân",
              "status": "REJECTED",
              "userEmail": "test.clh@gmail.com"
          }
      ]
    }
  ```
    - `200 No leave request found`
        ```json
          {
            "statusCode": 200,
            "message": "No Leave Requests Found"     
          }
        ```
    - `500 Exceptional error`
      ```json
      {
         "status": 500,
         "message": "Error fetching leave request: "     
      }
      ```

#### 4. View All Leave Requests
- **URL**: `GET leave-requests/view`
- **Description**: Xem tất cả yêu cầu xin nghỉ.
- **Path variable**: int page and int size
- **Response**:
    - `200 OK`: View successfully.
  ```json
    {
      "status": 200,
      "message": "Fetching all leave requests successfully",
      "leaveRequestDTOList": [
          {
              "id": 4,
              "startDate": "2025-04-01",
              "endDate": "2025-04-10",
              "reason": "Nghỉ phép cá nhân",
              "status": "REJECTED",
              "userEmail": "test.clh@gmail.com"
          },
          {
              "id": 5,
              "startDate": "2025-06-18",
              "endDate": "2025-06-22",
              "reason": "Nghỉ phép cá nhân",
              "status": "ACCEPTED",
              "userEmail": "test.clh@gmail.com"
          },
          {
              "id": 8,
              "startDate": "2025-06-16",
              "endDate": "2025-06-25",
              "reason": "Nghỉ phép cá nhân",
              "status": "REJECTED",
              "userEmail": "test.clh@gmail.com"
          }
      ]
    }
  ```
    - `200 No leave request found`
        ```json
          {
            "statusCode": 200,
            "message": "No Leave Requests Found"     
          }
        ```
    - `500 Exceptional error`
      ```json
      {
         "status": 500,
         "message": "Error fetching leave request: "     
      }
      ```

#### 5. Accept/Reject leave request
- **URL**: `GET leave-requests/accept/{id}` / `GET leave-requests/reject/{id}`
- **Description**: Trả lời đơn xin nghỉ của 1 người dùng.
- **Path variable**: long id
- **Response**:
    - `200 OK`: Accept/Reject successfully.
      ```json
      {
          "status": 200,
          "message": "Leave request has been handled successfully"
      }
      ```
    - `404 Not found`: Leave request not found.
      ```json
      {
          "status": 404,
          "message": "Leave request fetched successfully"
      }
      ```
    - `500 Exceptional error`
      ```json
      {
         "status": 500,
         "message": "Error fetching leave request: "     
      }
      ```
      
#### 6. Delete Leave Request By ID
- **URL**: `DELETE leave-requests/delete/{leave_request_id}`
- **Description**: Xóa leave request.
- **Path variable**: long id
- **Response**:
    - `200 OK`: Deleted successfully.
      ```json
      {
          "status": 200,
          "message": "Leave request deleted successfully"
      }
      ```
    - `404 Leave Request Not Found`
        ```json
          {
            "statusCode": 404,
            "message": "Leave Request Not Found"     
          }
        ```


#### 7. Employee view all its own Leave Requests
- **URL**: `GET leave-requests/employee/view?page=0&size=5`
- **Description**: Xem tất cả Leave Request của Employee đang đăng nhập.
  - **Response**:
  - **Params (optionally - có param này hay không dều được)**:
      - *page*: `0 (Default value is 0)`
      - *size*: `5 (Default value is 5)`
      - `200 OK`: View successfully.
        ```json
        {
            "status": 200,
            "message": "Your leave requests retrieved successfully",
            "currentPage": 0,
            "totalPages": 1,
            "totalElements": 2,
            "leaveRequestDTOList": [
                {
                    "id": 1,
                    "startDate": "2025-03-27",
                    "endDate": "2025-03-28",
                    "reason": "Another Vacation",
                    "status": "Pending",
                    "userEmail": "johndoe90@example.com"
                },
                {
                    "id": 3,
                    "startDate": "2025-03-27",
                    "endDate": "2025-03-28",
                    "reason": "New Vacation",
                    "status": "Pending",
                    "userEmail": "johndoe90@example.com"
                }
            ]
        }
        ```


#### 8. Employee view all its own Leave Requests sorted by Dates
- **URL**: `GET leave-requests/employee/view?startDate=2025-03-01&endDate=2025-03-30&page=0&size=5`
- **Description**: Xem tất cả Leave Request của Employee đang đăng nhập.
- **Params**:
  - *startDate*: `2025-03-01`
  - *endDate*: `2025-03-30`
  - *page*: `0 (Default value is 0) - có param này hay không đều được`
  - *size*: `5 (Default value is 5) - có param này hay không đều được`
  - **Response**:
      - `200 OK`: View successfully.
        ```json
        {
            "status": 200,
            "message": "Your leave requests retrieved successfully",
            "currentPage": 0,
            "totalPages": 1,
            "totalElements": 2,
            "leaveRequestDTOList": [
                {
                    "id": 1,
                    "startDate": "2025-03-27",
                    "endDate": "2025-03-28",
                    "reason": "Another Vacation",
                    "status": "Pending",
                    "userEmail": "johndoe90@example.com"
                },
                {
                    "id": 3,
                    "startDate": "2025-03-27",
                    "endDate": "2025-03-28",
                    "reason": "New Vacation",
                    "status": "Pending",
                    "userEmail": "johndoe90@example.com"
                }
            ]
        }
        ```