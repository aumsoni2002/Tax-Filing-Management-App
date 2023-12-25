# Tax Filing Management App

Welcome to the Tax Filing Management App, a cutting-edge Android application designed for efficient tax filing management. This app streamlines the process of handling potential customers interested in tax filing services, providing a user-friendly interface for administrators to manage customer data seamlessly.

## Features

### 1. Dynamic Home Screen

- **Customer Data Initialization**: If the app is running for the first time and RoomDB is empty, the app intelligently populates the RoomDB with provided customer data.
- **Process Status Visualization**: The Home Screen displays a list of customers using a Recycler View, showcasing actionable information such as Name, Phone, City, Company Name, and Process Status.
- **Intuitive Color Coding**: Each row's background color changes dynamically based on the process status, enhancing visual clarity:
  - AWAITED: Yellow tone
  - COMPLETED: Green tone
  - DENIED: Red tone
- **Effortless Navigation**: Tapping on a row in the Recycler View seamlessly takes the admin to the Customer Detail Screen.

### 2. Customer Detail Screen

- **Comprehensive Customer Details**: The Customer Detail Screen provides a detailed view of each customer's information.
- **Real-time Modification**: Admins can modify the Phone number and Process Status of a customer directly from this screen.
- **Persistence through RoomDB**: All modifications persist using RoomDB and seamlessly reflect on the Home Screen.
- **User-friendly Navigation**: Admins can easily navigate back to the Home Screen using the Back button.

### 3. Advanced User Interaction

- **Swipe-to-Delete Functionality**: Admins can swipe-to-delete a customer, with a confirmation prompt to avoid accidental data deletion. Updated data persists in RoomDB.
- **Smart Process Status Update**: Admins can change the process status for a customer on the Customer Detail Screen, providing flexibility and control.

## Technical Details

### Room Database

The app leverages Room, the Android persistence library, for efficient data storage and retrieval. The `AppDatabase` class manages the Room database, while the `UserDAO` interface defines data access operations.

### Model Class

The `User` class serves as the data model, storing customer information such as full name, phone, city, company name, and process status. Parcelable implementation ensures easy data transfer between activities.

### RecyclerView and Adapter

The app employs a RecyclerView with a custom adapter (`RecyclerAdapter`) to dynamically display customer data on the Home Screen. The adapter handles item click events and updates the UI based on process status.

### User Service

The `UserService` class encapsulates database operations, such as inserting, updating, and deleting users. Asynchronous threads ensure smooth performance, and callback interfaces handle operation outcomes.

### MainActivity and CustomerDetails Activity

The `MainActivity` orchestrates the Home Screen, fetching and displaying customer data. Swipe-to-delete functionality is implemented using ItemTouchHelper. The `CustomerDetails` activity provides a detailed view of customer information with options to update data.

## Academic Integrity

This repository is a testament to academic achievement and adherence to integrity. It was created for educational purposes, showcasing proficiency in web development concepts and practices. It is essential to maintain academic integrity and not engage in any form of plagiarism or cheating. Feel free to explore the codebase and run the application to experience the functionalities firsthand.


---

**Note:** The Tax Filing Management App is a showcase of Android development skills and is not associated with any real tax filing services.
