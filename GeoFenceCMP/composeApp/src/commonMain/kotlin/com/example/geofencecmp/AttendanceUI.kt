package com.example.geofencecmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geofencecmp.auth.AuthService
import com.example.geofencecmp.geofence.GeofenceManagerFactory
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onNavigateToSignup: () -> Unit) {
    var email by remember { mutableStateOf("ss@ss.com") }
    var password by remember { mutableStateOf("123456") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val authService = remember { AuthService() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome Back!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Login to your account",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                isLoading = true
                errorMessage = null
                scope.launch {
                    val result = authService.signIn(email, password)
                    isLoading = false
                    if (result.isSuccess) {
                        onLoginSuccess()
                    } else {
                        errorMessage = result.exceptionOrNull()?.message ?: "Login failed"
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text("LOGIN")
            }
        }

        TextButton(onClick = onNavigateToSignup, enabled = !isLoading) {
            Text("Don't have an account? Sign Up")
        }
    }
}

@Composable
fun SignupScreen(onSignupSuccess: () -> Unit, onNavigateToLogin: () -> Unit) {
    var fullName by remember { mutableStateOf("Shiv") }
    var email by remember { mutableStateOf("ss3@ss.com") }
    var mobileNumber by remember { mutableStateOf("9879879879") }
    var empId by remember { mutableStateOf("GEO_987") }
    var designation by remember { mutableStateOf("Associate") }
    var password by remember { mutableStateOf("123456") }
    var confirmPassword by remember { mutableStateOf("123456") }
    var passwordVisible by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val authService = remember { AuthService() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Join GeoFence Attendance",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }

        if (errorMessage != null) {
            item {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
        
        item {
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        item {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }

        item {
            OutlinedTextField(
                value = mobileNumber,
                onValueChange = { mobileNumber = it },
                label = { Text("Mobile Number") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
        }

        item {
            OutlinedTextField(
                value = empId,
                onValueChange = { empId = it },
                label = { Text("EMP ID") },
                leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        item {
            OutlinedTextField(
                value = designation,
                onValueChange = { designation = it },
                label = { Text("Designation") },
                leadingIcon = { Icon(Icons.Default.Work, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
        
        item {
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )
        }

        item {
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )
        }
        
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = {
                        if (password != confirmPassword) {
                            errorMessage = "Passwords do not match"
                            return@Button
                        }
                        
                        isLoading = true
                        errorMessage = null
                        
                        scope.launch {
                            val result = authService.signUp(
                                name = fullName,
                                email = email,
                                mobile = mobileNumber,
                                emp_id = empId,
                                designation = designation,
                                pass = password
                            )
                            
                            isLoading = false
                            if (result.isSuccess) {
                                onSignupSuccess()
                            } else {
                                errorMessage = result.exceptionOrNull()?.message ?: "Signup failed"
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("SIGN UP")
                    }
                }

                TextButton(onClick = onNavigateToLogin, enabled = !isLoading) {
                    Text("Already have an account? Login")
                }
            }
        }
    }
}

@Composable
fun AttendanceScreen() {
  //  val locationService = remember { LocationService() }
   // val currentLocation by locationService.getCurrentLocation()
    var isInsideGeofence by remember { mutableStateOf(false) }
    var isCheckedIn by remember { mutableStateOf(false) }

   /* currentLocation?.let {
        val distance = distanceBetween(
            it.latitude,
            it.longitude,
            17.489714,
            78.331193
        )
        isInsideGeofence = distance < 10f
    }*/


    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Workplace Attendance", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)

        // Geofence Status Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (isInsideGeofence) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    if (isInsideGeofence) Icons.Default.CheckCircle else Icons.Default.Error,
                    contentDescription = null,
                    tint = if (isInsideGeofence) Color(0xFF2E7D32) else Color(0xFFC62828)
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        if (isInsideGeofence) "Inside Job Site" else "Outside Job Site",
                        fontWeight = FontWeight.Bold,
                        color = if (isInsideGeofence) Color(0xFF2E7D32) else Color(0xFFC62828)
                    )
                    Text(
                        if (isInsideGeofence) "You can now mark attendance." else "Move closer to the site to check-in.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        Spacer(Modifier.height(40.dp))

        // Large Attendance Button
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(if (isInsideGeofence) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.3f))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    isCheckedIn = !isCheckedIn
                    GeofenceManagerFactory.create().addGeofence( id = "SITE_A",
                        lat = 17.489714, // Replace with your lat
                        lng = 78.331193, // Replace with your lng
                        radius = 10f )
                          },
                enabled = isInsideGeofence,
                modifier = Modifier.fillMaxSize(),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isCheckedIn) Color.Red else MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    if (isCheckedIn) "CHECK OUT" else "CHECK IN",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }

        // Toggle for Simulation
        Spacer(Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Simulate Inside Geofence")
            Switch(checked = isInsideGeofence, onCheckedChange = { isInsideGeofence = it })
        }
    }
}

@Composable
fun ManagerDashboardScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Manager Dashboard", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard("On-Site", "12", Icons.Default.People, Modifier.weight(1f))
            StatCard("Off-Site", "4", Icons.Default.PersonOff, Modifier.weight(1f))
        }

        Spacer(Modifier.height(24.dp))
        Text("Live Activity", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(sampleLogs) { log ->
                ActivityLogItem(log)
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String, icon: ImageVector, modifier: Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text(value, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text(title, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun ActivityLogItem(log: ActivityLog) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(log.name, fontWeight = FontWeight.Bold)
                Text(log.action, style = MaterialTheme.typography.bodySmall)
            }
            Text(log.time, style = MaterialTheme.typography.labelSmall)
        }
    }
}

data class ActivityLog(val name: String, val action: String, val time: String)
val sampleLogs = listOf(
    ActivityLog("John Doe", "Checked In (Site A)", "08:30 AM"),
    ActivityLog("Jane Smith", "Checked In (Site A)", "08:45 AM"),
    ActivityLog("Mike Ross", "Exited Geofence (Alert)", "10:15 AM"),
    ActivityLog("Harvey Specter", "Checked Out", "05:00 PM")
)

@Preview
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(onLoginSuccess = {}, onNavigateToSignup = {})
    }
}

@Preview
@Composable
fun SignupScreenPreview() {
    MaterialTheme {
        SignupScreen(onSignupSuccess = {}, onNavigateToLogin = {})
    }
}

@Preview
@Composable
fun AttendanceScreenPreview() {
    MaterialTheme {
        AttendanceScreen()
    }
}
